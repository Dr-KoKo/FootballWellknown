package com.a203.sixback.match.vo;

import com.a203.sixback.db.enums.MatchStatus;
import com.a203.sixback.team.vo.MatchVO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchStatusVO {
    private MatchVO matchVO;
    private MatchStatus matchStatus;

}
