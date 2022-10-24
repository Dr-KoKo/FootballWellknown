package com.a203.sixback.scheduler.task;

import com.a203.sixback.match.MatchService;
import com.a203.sixback.scheduler.MainScheduler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledFuture;

@Slf4j
public class InitTask implements Runnable{
    private long matchId;
    private String apiKey;

    private MatchService matchService;

    public InitTask(long matchId, String apiKey, MatchService matchService) {
        this.matchId = matchId;
        this.apiKey = apiKey;
        this.matchService = matchService;
    }

    @Override
    public void run() {
        log.info("InitTask 실행");
        Runnable task = new MatchTask(matchId, apiKey, matchService);

        try {
            MainScheduler.getInstance().start(task, "*/1 * * * * *", matchId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
