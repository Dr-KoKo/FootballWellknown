package com.a203.sixback.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
public class SchedulerService {
    private Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @Autowired
    private TaskScheduler taskScheduler;

    public void register(Long scheduleId, ScheduledFuture<?> task) {
        if(scheduledTasks.containsKey(scheduleId)) {
            remove(scheduleId);
        }
        scheduledTasks.put(scheduleId, task);
    }

    public void remove(Long scheduleId) {
        log.info(scheduleId+"를 종료합니다.");
        scheduledTasks.get(scheduleId).cancel(true);
    }
}
