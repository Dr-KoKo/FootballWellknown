package com.a203.sixback.scheduler.task;

import com.a203.sixback.db.entity.PointLog;
import com.a203.sixback.db.entity.Predict;
import com.a203.sixback.db.enums.MatchResult;
import com.a203.sixback.db.enums.MessageType;
import com.a203.sixback.db.redis.MatchCacheRepository;
import com.a203.sixback.db.repo.PointLogRepo;
import com.a203.sixback.db.repo.PredictRepo;
import com.a203.sixback.match.MatchService;
import com.a203.sixback.ranking.RankingService;
import com.a203.sixback.scheduler.MainScheduler;
import com.a203.sixback.scheduler.SchedulerService;
import com.a203.sixback.socket.BaseMessage;
import com.a203.sixback.socket.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MatchTask implements Runnable {
    private long matchId;
    private SchedulerService schedulerService;

    public MatchTask(long matchId, SchedulerService schedulerService) {
        this.matchId = matchId;
        this.schedulerService = schedulerService;
    }

    @Override
    public void run() {
        log.info("MatchTask  실행");
        // TODO: APIfootball에서 event api를 불러온다.
        try {
            if (schedulerService.match(matchId))
                MainScheduler.getInstance().stop(matchId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
