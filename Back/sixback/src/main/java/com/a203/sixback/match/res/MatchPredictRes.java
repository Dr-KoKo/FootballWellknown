package com.a203.sixback.match.res;

import com.a203.sixback.match.vo.MatchPredictVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MatchPredictRes extends BaseResponseBody {
    MatchPredictVO result = new MatchPredictVO();

    public static MatchPredictRes of(Integer statusCode, String message, MatchPredictVO result){
        MatchPredictRes res = new MatchPredictRes();
        res.setResult(result);
        res.setMessage(message);
        res.setStatusCode(statusCode);
        return res;
    }

}
