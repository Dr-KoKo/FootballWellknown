package com.a203.sixback.match.res;

import com.a203.sixback.match.vo.MatchStatusVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MatchDetailRes extends BaseResponseBody {
    MatchStatusVO result = new MatchStatusVO();

    public static MatchDetailRes of(Integer statusCode, String message, MatchStatusVO result){
        MatchDetailRes res = new MatchDetailRes();
        res.setResult(result);
        res.setMessage(message);
        res.setStatusCode(statusCode);
        return res;
    }
}
