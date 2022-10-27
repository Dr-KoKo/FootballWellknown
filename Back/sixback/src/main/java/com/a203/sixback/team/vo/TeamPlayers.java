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
    private List<PlayerVO> fws;
    private List<PlayerVO> dfs;
    private List<PlayerVO> mfs;
    private List<PlayerVO> gks;
    private String coachName;
    private String coachImage;
    private int coachAge;
    private String country;
}
