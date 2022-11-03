package com.a203.sixback.team.res;

import com.a203.sixback.team.vo.PlayerRankVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
public class PlayerRankRes extends BaseResponseBody {
    PlayerRankVO result = new PlayerRankVO();

    public static PlayerRankRes of(Integer statusCode, String message,PlayerRankVO result){
        PlayerRankRes res = new PlayerRankRes();
        res.setMessage(message);
        res.setStatusCode(statusCode);
        res.setResult(result);

        return res;
    }
}
