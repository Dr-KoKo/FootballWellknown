package com.a203.sixback.team.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerVO {
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
}
