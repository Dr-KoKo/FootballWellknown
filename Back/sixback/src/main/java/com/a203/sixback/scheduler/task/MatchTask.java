package com.a203.sixback.scheduler.task;

import com.a203.sixback.db.entity.Matches;
import com.a203.sixback.db.entity.PointLog;
import com.a203.sixback.db.entity.Predict;
import com.a203.sixback.db.enums.MatchResult;
import com.a203.sixback.db.redis.MatchCacheRepository;
import com.a203.sixback.db.repo.PointLogRepo;
import com.a203.sixback.db.repo.PredictRepo;
import com.a203.sixback.match.MatchService;
import com.a203.sixback.scheduler.vo.GoalScorer;
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
                String matchStatus = jsonObject.get("match_status").toString();


                List<GoalScorer> goalscorer = new ArrayList<>();
                for (Object obj : (JSONArray) jsonObject.get("goalscorer")) {
                    JSONObject goalScoerer = (JSONObject) obj;

                    GoalScorer gs = new GoalScorer();

                    gs.setTime(goalScoerer.get("time").toString());
                    gs.setScore(goalScoerer.get("score").toString());
                    gs.setInfo(goalScoerer.get("info").toString());
                    gs.setHome_scorer(goalScoerer.get("home_scorer").toString());
                    gs.setHome_scorer_id(goalScoerer.get("home_scorer_id").toString());
                    gs.setHome_assist(goalScoerer.get("home_assist").toString());
                    gs.setHome_assist_id(goalScoerer.get("home_assist_id").toString());
                    gs.setAway_scorer(goalScoerer.get("away_scorer").toString());
                    gs.setAway_scorer_id(goalScoerer.get("away_scorer_id").toString());
                    gs.setAway_assist(goalScoerer.get("away_assist").toString());
                    gs.setAway_assist_id(goalScoerer.get("time").toString());

                    goalscorer.add(gs);
                }

                String redis = matchCacheRepository.getMatch(matchId);

                int goals = 0;


                if (redis == null) {

                    matchCacheRepository.setMatch(matchId, 0);
                } else {
                    goals = Integer.parseInt(redis);
                }

                log.info("{}", goals);
                log.info("{}", goalscorer.size());

                if (goals != goalscorer.size()) {
                    log.info("득점하였습니다.");

                    matchCacheRepository.setMatch(matchId, goals + 1);
                    messageService.message(new Message("goal", "admin", String.valueOf(matchId), goalscorer.get(goals).getScore()));
                }

                if ("Finished".equals(matchStatus)) {
                    log.info("경기가 끝났습니다.");
                    matchCacheRepository.getAndDeleteMatch(matchId);
                    messageService.message(new Message("notice", "admin", String.valueOf(matchId), "경기가 종료되었습니다."));
                    MainScheduler.getInstance().stop(matchId);

                    Integer homeTeamScore = Integer.parseInt(jsonObject.get("match_hometeam_score").toString());
                    Integer awayTeamScore = Integer.parseInt(jsonObject.get("match_awayteam_score").toString());

                    MatchResult result = homeTeamScore > awayTeamScore ?
                            MatchResult.HOME_WIN : homeTeamScore == awayTeamScore ?
                            MatchResult.DRAW : MatchResult.AWAY_WIN;

                    log.info("배점을 시작합니다.");
                    givePoint(matchId, result);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    private void givePoint(Long matchId, MatchResult result) {
        List<Predict> correctPredictList = predictRepo.findAllByMatches_Id(matchId).stream().filter(x->result.toString().equals(x.getMatchResult().toString())).collect(Collectors.toList());;

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
