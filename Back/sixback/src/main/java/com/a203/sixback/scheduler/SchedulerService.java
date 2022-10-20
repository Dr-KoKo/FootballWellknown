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
@Configuration
@EnableScheduling
@ComponentScan
@Component
public class SchedulerService {
    private Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @Autowired
    private TaskScheduler taskScheduler;

    private static AnnotationConfigApplicationContext CONTEXT = null;

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    public static SchedulerService getInstance() {
        if (!isValidBean()) {
            CONTEXT = new AnnotationConfigApplicationContext(SchedulerService.class);
        }

        return CONTEXT.getBean(SchedulerService.class);
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    public void start(Runnable task, String scheduleExpression, long matchId) throws Exception {
        log.info("Scheduler start");
        register(matchId, scheduler.schedule(task, new CronTrigger(scheduleExpression)));
    }

    public void start(Runnable task, Long delay, long matchId) throws Exception {
        register(matchId, scheduler.scheduleWithFixedDelay(task, delay));
    }

    public void stopAll() {
        scheduler.shutdown();
        CONTEXT.close();
    }

    private static boolean isValidBean() {
        if (CONTEXT == null || !CONTEXT.isActive()) {
            return false;
        }

        try {
            CONTEXT.getBean(SchedulerService.class);
        } catch (NoSuchBeanDefinitionException ex) {
            return false;
        }

        return true;
    }

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
}
