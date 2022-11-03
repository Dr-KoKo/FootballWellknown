package com.a203.sixback.scheduler.task;

import com.a203.sixback.db.redis.MatchCacheRepository;
import com.a203.sixback.db.repo.PointLogRepo;
import com.a203.sixback.db.repo.PredictRepo;
import com.a203.sixback.match.MatchService;
//import com.a203.sixback.redis.RedisService;
import com.a203.sixback.scheduler.MainScheduler;
import com.a203.sixback.socket.MessageService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledFuture;

@Slf4j
public class InitTask implements Runnable{
    private long matchId;
    private MessageService messageService;
    private MatchService matchService;
    private MatchCacheRepository matchCacheRepository;
    private PointLogRepo pointLogRepo;
    private PredictRepo predictRepo;

    public InitTask(long matchId, MessageService messageService, MatchService matchService, MatchCacheRepository matchCacheRepository, PointLogRepo pointLogRepo, PredictRepo predictRepo) {
        this.matchId = matchId;
        this.messageService = messageService;
        this.matchService = matchService;
        this.matchCacheRepository = matchCacheRepository;
        this.pointLogRepo = pointLogRepo;
        this.predictRepo = predictRepo;
    }

    @Override
    public void run() {
        log.info("InitTask 실행");
        Runnable task = new MatchTask(matchId, messageService, matchService, matchCacheRepository, pointLogRepo, predictRepo);

        try {
            MainScheduler.getInstance().start(task, "*/1 * * * * *", matchId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
