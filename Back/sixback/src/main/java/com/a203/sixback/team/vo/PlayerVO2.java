package com.a203.sixback.team.vo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerVO2 {
    private long id;
    private String name;
    private String image;
    private int goals;
    private int assists;
    private int joinMatches;
    private String position;
    private String teamName;
}
