package com.a203.sixback.scheduler.task;

import com.a203.sixback.scheduler.MainScheduler;
import com.a203.sixback.scheduler.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ScheduledFuture;

public class InitTask implements Runnable{
    private long matchId;
    @Autowired
    private SchedulerService schedulerService;

    public InitTask(long matchId) {
        this.matchId = matchId;
    }

    @Override
    public void run() {
        Runnable task = new MatchTask(matchId);

        try {
            ScheduledFuture<?> future = MainScheduler.getInstance().start(task, 1000L);
            schedulerService.register(matchId, future);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
