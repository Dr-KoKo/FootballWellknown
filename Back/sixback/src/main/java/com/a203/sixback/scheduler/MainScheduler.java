package com.a203.sixback.scheduler;

import com.a203.sixback.scheduler.config.CustomThreadPoolTaskScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
@EnableScheduling
@ComponentScan
@Component
public class MainScheduler {

    private static AnnotationConfigApplicationContext CONTEXT = null;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    public static MainScheduler getInstance() {
        if (!isValidBean()) {
            CONTEXT = new AnnotationConfigApplicationContext(MainScheduler.class);
        }

        return CONTEXT.getBean(MainScheduler.class);
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    public void start(Runnable task, String scheduleExpression, long matchId) throws Exception {
        schedulerService.register(matchId, scheduler.schedule(task, new CronTrigger(scheduleExpression)));
    }

    public void start(Runnable task, Long delay, long matchId) throws Exception {
        schedulerService.register(matchId, scheduler.scheduleWithFixedDelay(task, delay));
    }

    public void stop(long matchId) {
        schedulerService.remove(matchId);
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
            CONTEXT.getBean(MainScheduler.class);
        } catch (NoSuchBeanDefinitionException ex) {
            return false;
        }

        return true;
    }
}
