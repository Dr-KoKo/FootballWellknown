package com.a203.sixback.scheduler.task;

import com.a203.sixback.scheduler.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ScheduledFuture;

@Slf4j
public class InitTask implements Runnable{
    private long matchId;
    @Autowired
    private SchedulerService schedulerService;

    public InitTask(long matchId) {
        this.matchId = matchId;
    }

    @Override
    public void run() {
        log.info("InitTask 실행");
        Runnable task = new MatchTask(matchId);

        try {
            SchedulerService.getInstance().start(task, "*/1 * * * * *", matchId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
