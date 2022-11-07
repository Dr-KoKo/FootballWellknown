package com.a203.sixback.match.res;

import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchRoundRes extends BaseResponseBody {
    int result = 0;
    public static MatchRoundRes of(Integer statusCode,  String message, int result){
        MatchRoundRes res = new MatchRoundRes();
        res.setResult(result);
        res.setMessage(message);
        res.setStatusCode(statusCode);
        return res;
    }
}
