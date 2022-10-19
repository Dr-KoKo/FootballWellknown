package com.a203.sixback.team.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamDetVO {
    private TeamInfo teamInfo;
    private List<MatchVO> finMatch;
    private MatchVO yetMatch;
}
