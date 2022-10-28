package com.a203.sixback.match.res;

import com.a203.sixback.match.vo.TeamBoardVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AllTeamBoardRes extends BaseResponseBody {
    List<TeamBoardVO> result = new ArrayList<>();

    public static AllTeamBoardRes of(Integer statusCode, String message, List<TeamBoardVO> result){
        AllTeamBoardRes res = new AllTeamBoardRes();
        res.setMessage(message);
        res.setStatusCode(statusCode);
        res.setResult(result);

        return res;
    }
}
