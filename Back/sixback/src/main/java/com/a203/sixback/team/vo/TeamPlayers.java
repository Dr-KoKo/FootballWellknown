package com.a203.sixback.team.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamPlayers {
    private List<PlayerVO> players;
    private String coachName;
    private String coachImage;
    private int coachAge;
    private String country;
}
