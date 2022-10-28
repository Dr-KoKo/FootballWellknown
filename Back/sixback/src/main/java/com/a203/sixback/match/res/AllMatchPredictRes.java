package com.a203.sixback.match.res;

import com.a203.sixback.match.vo.MatchPredictVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AllMatchPredictRes extends BaseResponseBody {
    List<MatchPredictVO> result = new ArrayList<>();

    public static AllMatchPredictRes of(Integer statusCode, String message, List<MatchPredictVO> result){
        AllMatchPredictRes res = new AllMatchPredictRes();
        res.setResult(result);
        res.setMessage(message);
        res.setStatusCode(statusCode);
        return res;
    }
}
