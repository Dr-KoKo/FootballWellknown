package com.a203.sixback.match.vo;

import com.a203.sixback.match.vo.MatchDetVO;
import com.a203.sixback.match.vo.PlayerMatchVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsVO {
    List<PlayerMatchVO> players;
    MatchDetVO homeDet;
    MatchDetVO awayDet;
}
