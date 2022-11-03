package com.a203.sixback.match.vo;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerMatchVO {
    private long id;
//    private int userRate;
//    private int userCount;
//    private int expertRate;
    private int goal;
    private int assist;
//    private int position;
    private int shot;
    private int shotOn;
    private int pass;
    private int passOn;
    private int foul;
    private int crossed;
    private int crossedOn;
    private int dribble;
    private int dribbleOn;
    private int tackle;
    private int clear;
    private int yellow;
    private int red;
    private long match_id;
    private long player_id;
    private String playerName;
    //HOME AWAY 중 하나
    private String team;
//    private int sub;

}
