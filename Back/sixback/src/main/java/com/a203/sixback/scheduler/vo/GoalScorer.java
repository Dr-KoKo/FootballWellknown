package com.a203.sixback.scheduler.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoalScorer {
    String time;
    String score;
    String home_scorer;
    String home_scorer_id;
    String home_assist;
    String home_assist_id;
    String away_scorer;
    String away_scorer_id;
    String away_assist;
    String away_assist_id;
    String info;

    @Override
    public String toString() {
        return time + " : " + score;
    }
}
