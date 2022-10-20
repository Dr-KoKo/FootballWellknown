package com.a203.sixback.scheduler.task;

import com.a203.sixback.match.MatchService;
import com.a203.sixback.scheduler.MainScheduler;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MatchTask implements Runnable{
    private long matchId;
    private

    @Autowired
    MatchService matchService;

    public MatchTask(long matchId) {
        this.matchId = matchId;
    }

    @Override
    public void run() {
        log.info("MatchTask  실행");
        // TODO: APIfootball에서 event api를 불러온다.
        try {
            JSONArray jsonArray = matchService.getMatchEvent(matchId);

            for(Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) object;
                String matchStatus = jsonObject.get("match_status").toString();

                if("Finished".equals(matchStatus)) {
                    MainScheduler.getInstance().stop(matchId);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
