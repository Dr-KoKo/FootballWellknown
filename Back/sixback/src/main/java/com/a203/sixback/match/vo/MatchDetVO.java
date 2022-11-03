package com.a203.sixback.match.vo;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchDetVO {
    private String name;
    private String image;
    private int shot;
    private int shotOn;
    private int foul;
    private int corner;
    private int penalty;
    private int offside;
    private String possession;
    private String formation;
    private int yellow;
    private int red;
    private int save;
    private int pass;
    private int passOn;
}
