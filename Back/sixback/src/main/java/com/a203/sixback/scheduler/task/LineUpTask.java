package com.a203.sixback.scheduler.task;

import com.a203.sixback.match.MatchService;
import com.a203.sixback.scheduler.MainScheduler;

public class LineUpTask implements Runnable{
    private long matchId;
    private MatchService matchService;
    public LineUpTask(long matchId, MatchService matchService) {
        this.matchId = matchId;
        this.matchService = matchService;
    }

    @Override
    public void run() {
        try {
            matchService.saveLineUps(matchId / 2L);
            MainScheduler.getInstance().stop(matchId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
