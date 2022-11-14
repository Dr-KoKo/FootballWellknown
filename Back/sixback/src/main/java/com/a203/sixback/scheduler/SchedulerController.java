package com.a203.sixback.scheduler;


import com.a203.sixback.match.MatchService;
import com.a203.sixback.match.vo.MatchStatusVO;
import com.a203.sixback.ranking.RankingService;
import com.a203.sixback.scheduler.task.InitTask;
import com.a203.sixback.scheduler.task.LineUpTask;
import com.a203.sixback.team.vo.MatchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@EnableAsync
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;
    @Autowired(required = false)
    private MatchService matchService;
    @Autowired(required = false)
    private RankingService rankingService;

//    @Value("${SCHEDULER-SERVER}")
    private boolean isSchedulerServer = false;

    @Async
    @Scheduled(cron = "0 0 23 * * *")
//    @Scheduled(cron = "0 58 8 * * *")
    public void mainSchedule() throws Exception {
        log.info("SchedulerController Cron 실행");

        LocalDateTime now = LocalDateTime.now().plusDays(1);

        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        log.info("{}-{}-{}", year, month, day);

        try {
            if (isSchedulerServer) {
                registerMatchSchedule(year, month, day);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Async
    @Scheduled(cron = "0 0 0 * * *")
    public void dailyRankingRefreshSchedule() throws Exception {
        if (isSchedulerServer){
            rankingService.refreshDailyRanking();
            rankingService.resetRanking();
        }
    }

    @Async
    @Scheduled(cron = "0 0 0 * * 0")
    public void weeklyRankingRefreshSchedule() throws Exception {
        if (isSchedulerServer){
            rankingService.refreshWeeklyRanking();
            rankingService.resetRanking();
        }
    }

    private void registerMatchSchedule(int year, int month, int day) throws Exception {
        StringBuilder sb = new StringBuilder();

        List<MatchStatusVO> list = matchService.getMatchesByDate(year, month, day);

        log.info("{}-{}-{}의 경기 수: {}", year, month, day, list.size());

        for (MatchStatusVO matchStatusVo : list) {
            MatchVO matchVO = matchStatusVo.getMatchVO();
            long matchId = matchVO.getMatchId();
            String date = matchVO.getDate();

            log.info("MatchId : {}, MatchDate : {}", matchId, date);

            int minute = Integer.parseInt(date.substring(14, 16));
            int hour = Integer.parseInt(date.substring(11, 13));

            sb.setLength(0);

            sb.append(0).append(" ").append(minute).append(" ")
                    .append(hour).append(" ").append("*").append(" ")
                    .append("*").append(" ").append("*");

            Runnable task = new InitTask(matchId, schedulerService);

            log.info("Cron Trigger : {}", sb.toString());

            MainScheduler.getInstance().start(task, sb.toString(), matchId);

            minute = minute >= 30 ? minute - 30 : minute + 30;

            hour = minute >= 30 ? hour : hour == 0 ? 23 : hour - 1;

            sb.setLength(0);

            sb.append(0).append(" ").append(minute).append(" ")
                    .append(hour).append(" ").append("*").append(" ")
                    .append("*").append(" ").append("*");


            task = new LineUpTask(matchId, matchService);

            MainScheduler.getInstance().start(task, sb.toString(), matchId * 2L);
        }
    }
}
