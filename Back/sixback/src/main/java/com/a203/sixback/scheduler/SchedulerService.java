package com.a203.sixback.scheduler;

import com.a203.sixback.db.entity.PlayerEvaluate;
import com.a203.sixback.db.entity.Predict;
import com.a203.sixback.db.enums.MatchResult;
import com.a203.sixback.db.enums.MessageType;
import com.a203.sixback.db.redis.MatchCacheRepository;
import com.a203.sixback.db.repo.PlayerEvaluateRepo;
import com.a203.sixback.db.repo.PointLogRepo;
import com.a203.sixback.db.repo.PredictRepo;
import com.a203.sixback.match.MatchService;
import com.a203.sixback.ranking.RankingService;
import com.a203.sixback.socket.BaseMessage;
import com.a203.sixback.socket.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SchedulerService {

    @Autowired
    private MatchService matchService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RankingService rankingService;
    @Autowired
    private MatchCacheRepository matchCacheRepository;
    @Autowired
    private PointLogRepo pointLogRepo;
    @Autowired
    private PredictRepo predictRepo;
    @Autowired
    private PlayerEvaluateRepo playerEvaluateRepo;

    private Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();


    public void register(Long scheduleId, ScheduledFuture<?> task) {
        log.info("Schedule 등록. schedule Id : {}", scheduleId);
        if (scheduledTasks.containsKey(scheduleId)) {
            remove(scheduleId);
        }
        scheduledTasks.put(scheduleId, task);
    }

    public void remove(Long scheduleId) {
        log.info(scheduleId + "를 종료합니다.");
        scheduledTasks.get(scheduleId).cancel(true);
    }

    @Async
    public boolean match(long matchId) throws Exception {
        JSONArray jsonArray = matchService.getMatchEvent(matchId);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;


            checkEvent((JSONArray) jsonObject.get("goalscorer"), "Goal_", matchId);
            checkEvent((JSONArray) jsonObject.get("cards"), "Card_", matchId);

            JSONObject substitutions = (JSONObject) jsonObject.get("substitutions");

            checkEvent((JSONArray) substitutions.get("home"), "SubstitutionsHome_", matchId);
            checkEvent((JSONArray) substitutions.get("away"), "SubstitutionsAway_", matchId);

            return checkStatus(jsonObject, matchId);
        }

        return false;
    }

    private void checkEvent(JSONArray jsonArray, String prefix, long matchId) {
        String key = prefix + matchId;
        int size = jsonArray.size();
        int value = getRedis(key);

        if (value == size) return;

        JSONObject jsonObject = (JSONObject) jsonArray.get(value);
        matchCacheRepository.setMatch(key, value + 1);

        String data = "";

        if ("Goal_".equals(prefix)) {
            data = jsonObject.get("score").toString();

            matchService.saveGoals(jsonObject, matchId);
        } else if ("Card_".equals(prefix)) {
            String falut = "".equals(jsonObject.get("home_fault").toString()) ? jsonObject.get("away_fault").toString() : jsonObject.get("home_fault").toString();
            data = jsonObject.get("card").toString() + "<br>" + falut;

            matchService.saveCards(jsonObject, matchId);
        } else {
            data = jsonObject.get("time").toString() + "분" + "<br>교체   ( " + jsonObject.get("substitution").toString() + " )";


            if ("SubstitutionsHome_".equals(prefix)) {
                matchService.saveHomeSub(jsonObject, matchId);
            } else if ("SubstitutionsAway_".equals(prefix)) {
                matchService.saveAwaySub(jsonObject, matchId);
            }
        }

        sendMessage(data, matchId);
    }

    private boolean checkStatus(JSONObject jsonObject, long matchId) throws Exception {
        String matchStatus = jsonObject.get("match_status").toString();

        if ("".equals(matchStatus)) {

        }

        if ("Finished".equals(matchStatus)) {
            log.info("경기가 끝났습니다.");
            matchCacheRepository.getAndDeleteMatch("Goal_" + matchId);
            matchCacheRepository.getAndDeleteMatch("Card_" + matchId);
            matchCacheRepository.getAndDeleteMatch("SubstitutionsHome_" + matchId);
            matchCacheRepository.getAndDeleteMatch("SubstitutionsAway_" + matchId);
            sendMessage("경기가 종료되었습니다.", matchId);
            MainScheduler.getInstance().stop(matchId);

            Integer homeTeamScore = Integer.parseInt(jsonObject.get("match_hometeam_score").toString());
            Integer awayTeamScore = Integer.parseInt(jsonObject.get("match_awayteam_score").toString());

            try {
                matchService.savePlayerMatch(matchId);
                matchService.saveTeamMatch(matchId);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception();
            }

            MatchResult result = homeTeamScore > awayTeamScore ? MatchResult.HOME : homeTeamScore == awayTeamScore ? MatchResult.DRAW : MatchResult.AWAY;

            log.info("배점을 시작합니다.");
            givePoint(matchId, result);

            JSONArray statisticsObject = (JSONArray) ((JSONObject) matchService.getStatistics(matchId).get(matchId)).get("player_statistics");

            Map<String, Integer> statisticsMap = new HashMap<>();
            for (Object o : statisticsObject) {
                JSONObject object = (JSONObject) o;
                statisticsMap.put(object.get("player_key").toString(), Integer.parseInt(object.get("player_rating").toString()));
            }

            givePlayerPoint(matchId, statisticsMap);

            return true;
        }
        return false;
    }

    private void givePoint(Long matchId, MatchResult result) {
        List<Predict> correctPredictList = predictRepo.findAllByMatches_Id(matchId).stream().filter(x -> result.toString().equals(x.getMatchResult().toString())).collect(Collectors.toList());

        log.info("총 {}개의 데이터 저장 시작합니다.", correctPredictList.size());

        rankingService.addAllScore(correctPredictList, 10);
    }

    private void givePlayerPoint(Long matchId, Map<String, Integer> statisticsObject) {
        List<PlayerEvaluate> correctPredictList = playerEvaluateRepo.findByMatches_Id(matchId)
                .stream()
                .filter(x -> x.getScore() == statisticsObject.get(x.getPlayer().getId()))
                .collect(Collectors.toList());

        log.info("총 {}개의 데이터 저장 시작합니다.", correctPredictList.size());

        rankingService.addAllPlayerScore(correctPredictList, 10);

    }

    private int getRedis(String key) {
        int value = 0;

        String redis = matchCacheRepository.getMatch(key);

        if (redis == null) {
            matchCacheRepository.setMatch(key, 0);
        } else {
            value = Integer.parseInt(redis);
        }

        return value;
    }

    private void sendMessage(String data, long matchId) {
        BaseMessage message = BaseMessage.builder().type(MessageType.NOTICE).sender("System").channelId(String.valueOf(matchId)).data(data).build();
        log.info("message: {}", data);
        //      messageService.sendMessage(message);
    }
}
