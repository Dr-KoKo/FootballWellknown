package com.a203.sixback.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@EnableAsync
public class SchedulerController {
/*
    @Autowired
    private MatchService matchService;
*/

    @Async
//    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "30 30 13 * * *")
    public void mainSchedule() throws Exception {
        log.info("SchedulerController Cron 실행");

        Date now = new Date();

        int year = now.getYear();
        int month = now.getMonth();
        int day = now.getDay();
/*
        List<MatchStatusVO> list = matchService.getMatchesByDate(year, month, day);

        for(MatchStatusVO matchStatusVo : list) {
            MatchVO matchVO = matchStatusVo.getMatchVo();
            long matchId = matchVO.getMatchId();
            String date = matchVO.getDate();

            sb.setLength(0);

            sb.append(date.substring(17, 19)).append(" ").append(date.substring(14 16)).append(" ")
            .append(date.substring(11, 13)).append(" ").append(date.substring(8, 10)).append(" ")
            .append(date.substring(5, 7)).append(" ").append(date.substring(0, 4));

            Runnable task = new InitTask(matchId);

            ScheduledFuture<?> future = MainScheduler.getInstance().start(task, sb.toString());

        }
*/
    }
}
