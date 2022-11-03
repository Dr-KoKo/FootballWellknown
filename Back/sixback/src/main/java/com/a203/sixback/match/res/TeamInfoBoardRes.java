package com.a203.sixback.match.res;

import com.a203.sixback.match.vo.TeamBoardVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamInfoBoardRes extends BaseResponseBody {
    TeamBoardVO result = new TeamBoardVO();

    public static TeamInfoBoardRes  of(Integer statusCode, String message, TeamBoardVO result) {
        TeamInfoBoardRes res = new TeamInfoBoardRes();
        res.setMessage(message);
        res.setStatusCode(statusCode);
        res.setResult(result);

        return res;
    }
}
