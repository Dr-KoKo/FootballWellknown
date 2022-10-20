package com.a203.sixback.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@EnableScheduling
public class SchedulerController {
    @Autowired
    private SchedulerService schedulerService;
/*
    @Autowired
    private MatchService matchService;
*/

    @Scheduled(cron = "0 0 0 * * *")
    public void mainSchedule() {
        Date now = new Date();

        int year = now.getYear();
        int month = now.getMonth();
        int day = now.getDay();

/*
        List<MatchStatusVO> list = matchService.getMatchesByDay(year, month, day);

        for(MatchStatusVO matchStatusVo : list) {
            MatchVO matchVO = matchStatusVo.getMatchVo();
            long matchId = matchVO.getMatchId();
            String date = matchVO.getDate();

            Runnable task = new InitTask(matchId);

            ScheduledFuture<?> future = MainScheduler.getInstance().start(task, date);

            schedulerService.register(matchId, future);
        }
*/
    }
}
