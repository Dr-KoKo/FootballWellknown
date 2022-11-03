package com.a203.sixback.match.vo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchHistoryVO {
    private String time;
    private String history;
    private String teamType;
    private String mainName;
    private String subName;
    private String info;
}
