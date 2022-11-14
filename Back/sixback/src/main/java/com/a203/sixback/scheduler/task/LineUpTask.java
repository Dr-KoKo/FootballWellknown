package com.a203.sixback.scheduler.task;

import com.a203.sixback.match.MatchService;
import com.a203.sixback.scheduler.MainScheduler;
import com.a203.sixback.scheduler.SchedulerService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LineUpTask implements Runnable{
    private long matchId;
    private SchedulerService schedulerService;
    public LineUpTask(long matchId, SchedulerService schedulerService) {
        this.matchId = matchId;
        this.schedulerService = schedulerService;
    }

    @Override
    public void run() {
        log.info("LineUpTask 실행");
        try {
            schedulerService.saveLineUps(matchId);
            MainScheduler.getInstance().stop(matchId * 2L);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
