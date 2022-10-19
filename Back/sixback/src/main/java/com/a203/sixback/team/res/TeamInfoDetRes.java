package com.a203.sixback.team.res;

import com.a203.sixback.team.vo.TeamDet;
import com.a203.sixback.team.vo.TeamInfo;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class TeamInfoDetRes extends BaseResponseBody {
    TeamDet result = new TeamDet();
    public static TeamInfoDetRes of(Integer statusCode, String message, TeamDet result){
        TeamInfoDetRes res = new TeamInfoDetRes();
        res.setMessage(message);
        res.setStatusCode(statusCode);
        res.setResult(result);
        return res;
    }
}
