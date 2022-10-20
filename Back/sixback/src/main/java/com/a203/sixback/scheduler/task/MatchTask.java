package com.a203.sixback.scheduler.task;

import com.a203.sixback.scheduler.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;

public class MatchTask implements Runnable{

    private long matchId;
    @Autowired
    private SchedulerService schedulerService;

    public MatchTask(long matchId) {
        this.matchId = matchId;
    }

    @Override
    public void run() {
        // TODO: APIfootball에서 event api를 불러온다.
    }
}
