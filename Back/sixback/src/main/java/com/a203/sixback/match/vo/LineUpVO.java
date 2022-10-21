package com.a203.sixback.match.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LineUpVO {
    private String formation;
    private List<LineUp> lineUpList;
    private String teamType;
}
