package com.a203.sixback.match.res;

import com.a203.sixback.match.vo.LineUpVO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AllPlayersRes extends BaseResponseBody {
    List<LineUpVO> result = new ArrayList<>();
    public static AllPlayersRes of(Integer statusCode, String message,List<LineUpVO> result){
        AllPlayersRes res = new AllPlayersRes();
        res.setMessage(message);
        res.setResult(result);
        res.setStatusCode(statusCode);
        return res;
    }
}
