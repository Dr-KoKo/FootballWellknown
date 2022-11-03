package com.a203.sixback.match.res;

import com.a203.sixback.match.vo.MatchBoardVO;
import com.a203.sixback.match.vo.TeamBoardVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class AllMatchBoardRes extends BaseResponseBody {
    List<MatchBoardVO> result = new ArrayList<>();

    public static AllMatchBoardRes of(Integer statusCode, String message, List<MatchBoardVO> result){
        AllMatchBoardRes res = new AllMatchBoardRes();
        res.setMessage(message);
        res.setStatusCode(statusCode);
        res.setResult(result);

        return res;
    }
}
