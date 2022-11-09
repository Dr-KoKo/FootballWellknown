package com.a203.sixback.scheduler.task;

import com.a203.sixback.scheduler.MainScheduler;
import com.a203.sixback.scheduler.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class AsyncTask implements Runnable{

    private int id;

    private SchedulerService schedulerService;
    public AsyncTask(int id, SchedulerService schedulerService) {
        this.id = id;
        this.schedulerService = schedulerService;
    }

    @Override
    public void run() {
        log.info("AsyncTask 실행");
        schedulerService.async(id);
        MainScheduler.getInstance().stop(id);

    }
}
