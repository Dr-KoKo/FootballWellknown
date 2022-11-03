package com.a203.sixback.match.res;

import com.a203.sixback.match.vo.LineUpVO;
import com.a203.sixback.match.vo.MatchHistoryVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AllMatchHistoryRes extends BaseResponseBody {
    List<MatchHistoryVO> result = new ArrayList<>();

    public static AllMatchHistoryRes of(Integer statusCode, String message, List<MatchHistoryVO> result){
        AllMatchHistoryRes res = new AllMatchHistoryRes();
        res.setMessage(message);
        res.setResult(result);
        res.setStatusCode(statusCode);
        return res;
    }
}
