package com.a203.sixback.scheduler.task;

import com.a203.sixback.scheduler.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MatchTask implements Runnable{

    private long matchId;
    private int num = 0;
    @Autowired
    private SchedulerService schedulerService;

    public MatchTask(long matchId) {
        this.matchId = matchId;
    }

    @Override
    public void run() {
        log.info("MatchTask  실행");
        // TODO: APIfootball에서 event api를 불러온다.
        num++;

        if(num == 10)
            SchedulerService.getInstance().remove(matchId);
    }
}
