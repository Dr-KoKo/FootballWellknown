package com.a203.sixback.scheduler.task;

import com.a203.sixback.db.entity.PointLog;
import com.a203.sixback.db.entity.Predict;
import com.a203.sixback.db.enums.MatchResult;
import com.a203.sixback.db.redis.MatchCacheRepository;
import com.a203.sixback.db.repo.PointLogRepo;
import com.a203.sixback.db.repo.PredictRepo;
import com.a203.sixback.match.MatchService;
import com.a203.sixback.scheduler.MainScheduler;
import com.a203.sixback.socket.Message;
import com.a203.sixback.socket.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MatchTask implements Runnable {
    private long matchId;
    private MessageService messageService;
    private MatchService matchService;
    private MatchCacheRepository matchCacheRepository;
    private PointLogRepo pointLogRepo;
    private PredictRepo predictRepo;

    public MatchTask(long matchId, MessageService messageService, MatchService matchService, MatchCacheRepository matchCacheRepository, PointLogRepo pointLogRepo, PredictRepo predictRepo) {
        this.matchId = matchId;
        this.messageService = messageService;
        this.matchService = matchService;
        this.matchCacheRepository = matchCacheRepository;
        this.pointLogRepo = pointLogRepo;
        this.predictRepo = predictRepo;
    }

    @Override
    public void run() {
        log.info("MatchTask  실행");
        // TODO: APIfootball에서 event api를 불러온다.
        try {
            JSONArray jsonArray = matchService.getMatchEvent(matchId);

            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) object;


                checkEvent((JSONArray) jsonObject.get("goalscorer"), "goal");
                checkEvent((JSONArray) jsonObject.get("cards"), "cards");

                JSONObject substitutions = (JSONObject) jsonObject.get("substitutions");

                checkEvent((JSONArray) substitutions.get("home"), "substitutions_home");
                checkEvent((JSONArray) substitutions.get("away"), "substitutions_away");

                checkStatus(jsonObject);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    private void checkEvent(JSONArray jsonArray, String prefix) {
        String key = prefix + matchId;
        int size = jsonArray.size();
        int value = getRedis(key);

        if (value == size)
            return;

        JSONObject jsonObject = (JSONObject) jsonArray.get(value);
        matchCacheRepository.setMatch(key, value + 1);

        String sender = "admin";
        String channelId = String.valueOf(matchId);
        String data = null;

        if ("goal".equals(prefix)) {
            data = jsonObject.get("score").toString();

            matchService.saveGoals(jsonObject, matchId);
        } else if ("cards".equals(prefix)) {
            String falut = "".equals(jsonObject.get("home_fault").toString()) ? jsonObject.get("away_fault").toString() : jsonObject.get("home_fault").toString();
            data = jsonObject.get("card").toString() + "<br>" + falut;

            matchService.saveCards(jsonObject, matchId);
        } else {
            int time = Integer.parseInt(jsonObject.get("time").toString());
            data = time < 46 ? ("전반전 " + time + "분") : ("후반전 " + (time - 45) + "분");
            data += "<br>교체   ( " + jsonObject.get("substitution").toString() + " )";


            if ("substitutions_home".equals(prefix)) {
                matchService.saveHomeSub(jsonObject, matchId);
            } else if ("substitutions_away".equals(prefix)) {
                matchService.saveAwaySub(jsonObject, matchId);
            }
        }

        messageService.message(new Message(prefix, sender, channelId, data));
    }

    private void checkStatus(JSONObject jsonObject) throws Exception {
        String matchStatus = jsonObject.get("match_status").toString();

        if ("".equals(matchStatus)) {

        }

        if ("Finished".equals(matchStatus)) {
            log.info("경기가 끝났습니다.");
            matchCacheRepository.getAndDeleteMatch("goal" + matchId);
            matchCacheRepository.getAndDeleteMatch("card" + matchId);
            matchCacheRepository.getAndDeleteMatch("substitutions_home" + matchId);
            matchCacheRepository.getAndDeleteMatch("substitutions_away" + matchId);
            messageService.message(new Message("notice", "admin", String.valueOf(matchId), "경기가 종료되었습니다."));
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

            MatchResult result = homeTeamScore > awayTeamScore ?
                    MatchResult.HOME_WIN : homeTeamScore == awayTeamScore ?
                    MatchResult.DRAW : MatchResult.AWAY_WIN;

            log.info("배점을 시작합니다.");
            givePoint(matchId, result);
        }
    }

    @Transactional
    private void givePoint(Long matchId, MatchResult result) {
        List<Predict> correctPredictList = predictRepo.findAllByMatches_Id(matchId).stream().filter(x -> result.toString().equals(x.getMatchResult().toString())).collect(Collectors.toList());
        ;

        LocalDateTime now = LocalDateTime.now();

        List<PointLog> pointLogList = new ArrayList<>(correctPredictList.size());

        for (Predict predict : correctPredictList) {
            PointLog log = PointLog.builder()
                    .user(predict.getUser())
                    .mp(predict)
                    .point(1)
                    .distribute_time(now)
                    .build();
            pointLogList.add(log);
        }

        log.info("총 {}개의 데이터 저장 시작합니다.", pointLogList.size());

        pointLogRepo.saveAll(pointLogList);
    }

}
