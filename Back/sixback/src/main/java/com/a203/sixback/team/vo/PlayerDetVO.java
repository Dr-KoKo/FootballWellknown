package com.a203.sixback.team.vo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDetVO {
    private long id;
    private String name;
    private String image;
    private String birth;
    private int height;
    private int weight;
    private int goals;
    private int assists;
    private String country;
    private Integer number;
    private int joinMatches;
    private String position;
    private String history;
}
