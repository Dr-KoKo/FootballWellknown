package com.a203.sixback.team.res;

import com.a203.sixback.team.vo.TeamPlayers;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamPlayersRes extends BaseResponseBody {
    TeamPlayers result = new TeamPlayers();
    public static TeamPlayersRes of(Integer statusCode, String message, TeamPlayers result){
        TeamPlayersRes res = new TeamPlayersRes();
        res.setMessage(message);
        res.setStatusCode(statusCode);
        res.setResult(result);
        return res;
    }
}
