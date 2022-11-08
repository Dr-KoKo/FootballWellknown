package com.a203.sixback.match.res;

import com.a203.sixback.match.vo.PlayerMatchVO;
import com.a203.sixback.match.vo.StatisticsVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlayerMatchRes extends BaseResponseBody {
    StatisticsVO result = new StatisticsVO();

    public static PlayerMatchRes of(Integer statusCode, String message, StatisticsVO result){
        PlayerMatchRes res = new PlayerMatchRes();
        res.setResult(result);
        res.setMessage(message);
        res.setStatusCode(statusCode);
        return res;
    }
}
