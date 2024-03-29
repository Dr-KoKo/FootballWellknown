package com.a203.sixback.scheduler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Configuration
public class CustomThreadPoolTaskScheduler extends ThreadPoolTaskScheduler {

    private static final long serialVersionUID = 1L;

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
        if (period <= 0) {
            return null;
        }
        ScheduledFuture<?> future = super.scheduleAtFixedRate(task, period);
        return future;
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Date startTime, long period) {
        if (period <= 0) {
            return null;
        }
        ScheduledFuture<?> future = super.scheduleAtFixedRate(task, startTime, period);
        return future;
    }
}