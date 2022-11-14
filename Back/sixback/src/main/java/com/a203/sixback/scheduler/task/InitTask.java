package com.a203.sixback.scheduler.task;

import com.a203.sixback.db.redis.MatchCacheRepository;
import com.a203.sixback.db.repo.PointLogRepo;
import com.a203.sixback.db.repo.PredictRepo;
import com.a203.sixback.match.MatchService;
//import com.a203.sixback.redis.RedisService;
import com.a203.sixback.scheduler.MainScheduler;
import com.a203.sixback.scheduler.SchedulerService;
import com.a203.sixback.socket.MessageService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledFuture;

@Slf4j
public class InitTask implements Runnable{
    private long matchId;
    private SchedulerService schedulerService;

    public InitTask(long matchId, SchedulerService schedulerService) {
        this.matchId = matchId;
        this.schedulerService = schedulerService;
    }

    @Override
    public void run() {
        log.info("InitTask 실행");
        Runnable task = new MatchTask(matchId, schedulerService);

        try {
            MainScheduler.getInstance().start(task, "*/10 * * * * *", matchId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
