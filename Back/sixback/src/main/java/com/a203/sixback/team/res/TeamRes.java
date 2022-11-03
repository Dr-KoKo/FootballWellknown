package com.a203.sixback.team.res;

import com.a203.sixback.db.entity.Team;
import com.a203.sixback.team.vo.TeamInfo;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRes extends BaseResponseBody {
    TeamInfo result = new TeamInfo();
    public static TeamRes of(Integer statusCode, String message, TeamInfo result){
        TeamRes teamRes = new TeamRes();
        teamRes.setMessage(message);
        teamRes.setStatusCode(statusCode);
        teamRes.setResult(result);
        return teamRes;
    }
}
