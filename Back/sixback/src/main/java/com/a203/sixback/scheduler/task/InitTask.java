package com.a203.sixback.scheduler.task;

import com.a203.sixback.scheduler.MainScheduler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledFuture;

@Slf4j
public class InitTask implements Runnable{
    private long matchId;

    public InitTask(long matchId) {
        this.matchId = matchId;
    }

    @Override
    public void run() {
        log.info("InitTask 실행");
        Runnable task = new MatchTask(matchId);

        try {
            MainScheduler.getInstance().start(task, "*/1 * * * * *", matchId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
