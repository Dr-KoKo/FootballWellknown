package com.a203.sixback.scheduler;

import com.a203.sixback.match.MatchService;
import com.a203.sixback.match.vo.MatchStatusVO;
import com.a203.sixback.scheduler.task.InitTask;
import com.a203.sixback.team.vo.MatchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@EnableAsync
public class SchedulerController {
    @Autowired(required = false)
    private MatchService matchService;

    @Async
    @Scheduled(cron = "0 0 0 * * *")
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

        for(MatchStatusVO matchStatusVo : list) {
            MatchVO matchVO = matchStatusVo.getMatchVO();
            long matchId = matchVO.getMatchId();
            String date = matchVO.getDate();

            log.info("MatchId : {}, MatchDate : {}", matchId, date);

            sb.setLength(0);

            sb.append(0).append(" ").append(date.substring(14, 16)).append(" ")
            .append(date.substring(11, 13)).append(" ").append(date.substring(8, 10)).append(" ")
            .append(date.substring(5, 7)).append(" ").append("*");

            Runnable task = new InitTask(matchId);

            log.info("Cron Trigger : {}", sb.toString());

            MainScheduler.getInstance().start(task, sb.toString(), matchId);
        }
    }
}
