package com.a203.sixback.team.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatchVO {
    private int matchId;
    private String homeName;
    private String homeScore;
    private String awayTeam;
    private String awayScore;
}
