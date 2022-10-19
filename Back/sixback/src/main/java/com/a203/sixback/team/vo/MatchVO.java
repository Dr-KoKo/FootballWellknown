package com.a203.sixback.team.vo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchVO {
    private long matchId;
    private String date;
    private String home;
    private String homeImage;
    private String awayImage;
    private int homeScore;
    private String away;
    private int awayScore;
    private String stadium;
}
