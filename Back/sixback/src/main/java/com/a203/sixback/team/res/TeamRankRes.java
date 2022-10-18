package com.a203.sixback.team.res;

import com.a203.sixback.team.vo.TeamInfo;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamRankRes extends BaseResponseBody {
    List<TeamInfo> result = new ArrayList<>();


    public static TeamRankRes of(Integer statusCode, String message, List<TeamInfo> result){
        TeamRankRes res = new TeamRankRes();
        res.setResult(result);
        res.setMessage(message);
        res.setStatusCode(statusCode);
        return res;
    }
}
