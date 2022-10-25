package com.a203.sixback.team.res;

import com.a203.sixback.team.vo.TeamDetVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDetRes extends BaseResponseBody {
    TeamDetVO result = new TeamDetVO();
    public static TeamDetRes of(Integer statusCode, String message,TeamDetVO result){
        TeamDetRes teamDetRes = new TeamDetRes();
        teamDetRes.setMessage(message);
        teamDetRes.setStatusCode(statusCode);
        teamDetRes.setResult(result);
        return teamDetRes;
    }
}
