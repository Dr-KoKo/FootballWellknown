package com.a203.sixback.scheduler.task;

import com.a203.sixback.match.MatchService;
import com.a203.sixback.redis.RedisService;
import com.a203.sixback.scheduler.MainScheduler;
import com.a203.sixback.socket.MessageService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledFuture;

@Slf4j
public class InitTask implements Runnable{
    private long matchId;
    private MessageService messageService;
    private MatchService matchService;
    private RedisService redisService;

    public InitTask(long matchId, MessageService messageService, MatchService matchService, RedisService redisService) {
        this.matchId = matchId;
        this.messageService = messageService;
        this.matchService = matchService;
        this.redisService = redisService;
    }

    @Override
    public void run() {
        log.info("InitTask 실행");
        Runnable task = new MatchTask(matchId, messageService, matchService, redisService);

        try {
            MainScheduler.getInstance().start(task, "*/1 * * * * *", matchId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
