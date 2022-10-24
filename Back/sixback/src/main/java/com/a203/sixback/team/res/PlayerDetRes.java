package com.a203.sixback.team.res;

import com.a203.sixback.team.vo.PlayerDetVO;
import com.a203.sixback.team.vo.PlayerVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDetRes extends BaseResponseBody {
    PlayerDetVO result = new PlayerDetVO();
    public static PlayerDetRes of(Integer statusCode, String message, PlayerDetVO result){
        PlayerDetRes res = new PlayerDetRes();
        res.setResult(result);
        res.setMessage(message);
        res.setStatusCode(statusCode);
        return res;
    }
}
