package com.a203.sixback.scheduler.task;

import com.a203.sixback.match.MatchService;
import com.a203.sixback.scheduler.MainScheduler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LineUpTask implements Runnable{
    private long matchId;
    private MatchService matchService;
    public LineUpTask(long matchId, MatchService matchService) {
        this.matchId = matchId;
        this.matchService = matchService;
    }

    @Override
    public void run() {
        log.info("LineUpTask 실행");
        try {
            matchService.saveLineUps(matchId);
            MainScheduler.getInstance().stop(matchId * 2L);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
