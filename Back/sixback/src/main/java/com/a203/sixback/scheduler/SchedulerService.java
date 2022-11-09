package com.a203.sixback.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
public class SchedulerService {
    private Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public void register(Long scheduleId, ScheduledFuture<?> task) {
        log.info("Schedule 등록. schedule Id : {}", scheduleId);
        if(scheduledTasks.containsKey(scheduleId)) {
            remove(scheduleId);
        }
        scheduledTasks.put(scheduleId, task);
    }

    public void remove(Long scheduleId) {
        log.info(scheduleId+"를 종료합니다.");
        scheduledTasks.get(scheduleId).cancel(true);
    }

    public void async(int id) {
        for(int i = 0; i < 10000; i++) {
            log.info("Async Test {} : {}", id, i);
        }
    }
}
