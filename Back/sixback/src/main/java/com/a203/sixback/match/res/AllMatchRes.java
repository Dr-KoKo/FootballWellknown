package com.a203.sixback.match.res;

import com.a203.sixback.match.vo.MatchStatusVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AllMatchRes extends BaseResponseBody {
    List<MatchStatusVO> result = new ArrayList<>();

    public static AllMatchRes of(Integer statusCode, String message, List<MatchStatusVO> result){
        AllMatchRes res = new AllMatchRes();
        res.setResult(result);
        res.setMessage(message);
        res.setStatusCode(statusCode);
        return res;
    }
}
