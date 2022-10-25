package com.a203.sixback.match.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerEvaluateVO {
    private long id;
    private long matchId;
    private long userId;
    private long playerId;
    private int score;
}
