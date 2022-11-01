package com.a203.sixback.scheduler;

import com.a203.sixback.db.redis.MatchCacheRepository;
import com.a203.sixback.match.MatchService;
import com.a203.sixback.match.vo.MatchStatusVO;
//import com.a203.sixback.redis.RedisService;
import com.a203.sixback.scheduler.task.InitTask;
import com.a203.sixback.socket.Message;
import com.a203.sixback.socket.MessageService;
import com.a203.sixback.team.vo.MatchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@EnableAsync
public class SchedulerController {
    @Autowired(required = false)
    private MatchService matchService;

    @Autowired(required = false)
    private MessageService messageService;

//    @Autowired(required = false)
//    private RedisService redisService;

    private MatchCacheRepository matchCacheRepository;

    @Value("${API-KEY}")
    private String apiKey;

    @Async
 //   @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "0 11 17 * * *")
    public void mainSchedule() throws Exception {
        log.info("SchedulerController Cron 실행");

        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        log.info("{}-{}-{}", year, month, day);

        StringBuilder sb = new StringBuilder();

        List<MatchStatusVO> list = matchService.getMatchesByDate(year, month, day);

        log.info("{}의 경기 수: {}", now.toString(), list.size());

        long matchId = 1059375L;

        Runnable task = new InitTask(matchId, messageService, matchService, matchCacheRepository);

        sb.append("10 11 17 * * *");

        MainScheduler.getInstance().start(task, sb.toString(), matchId);
/*
        for(MatchStatusVO matchStatusVo : list) {
            MatchVO matchVO = matchStatusVo.getMatchVO();
            long matchId = matchVO.getMatchId();
            String date = matchVO.getDate();

            log.info("MatchId : {}, MatchDate : {}", matchId, date);

            sb.setLength(0);

            sb.append(0).append(" ").append(date.substring(14, 16)).append(" ")
            .append(date.substring(11, 13)).append(" ").append(date.substring(8, 10)).append(" ")
            .append(date.substring(5, 7)).append(" ").append("*");

            Runnable task = new InitTask(matchId, messageService, matchService, redisService);

            log.info("Cron Trigger : {}", sb.toString());

            MainScheduler.getInstance().start(task, sb.toString(), matchId);
        }*/
    }
}
