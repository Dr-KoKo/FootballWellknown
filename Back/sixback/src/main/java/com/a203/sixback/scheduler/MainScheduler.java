package com.a203.sixback.scheduler;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

@Configuration
@EnableScheduling
@ComponentScan
@Component
public class MainScheduler {

    private static AnnotationConfigApplicationContext CONTEXT = null;

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

    public ScheduledFuture<?> start(Runnable task, String scheduleExpression) throws Exception {
        return scheduler.schedule(task, new CronTrigger(scheduleExpression));
    }

    public ScheduledFuture<?> start(Runnable task, Long delay) throws Exception {
        return scheduler.scheduleWithFixedDelay(task, delay);
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