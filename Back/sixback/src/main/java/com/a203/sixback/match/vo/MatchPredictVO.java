package com.a203.sixback.match.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchPredictVO {
    private long id;
    private long matchId;
    private String userNickname;
    private long userId;
    private String whereWin;
}
