package com.a203.sixback.team.vo;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TeamInfo{
    private String name;
    private String image;
    private int win;
    private int draw;
    private int lose;
    private int goals;
    private int loseGoals;
    private int rank;
    private int pts;
}
