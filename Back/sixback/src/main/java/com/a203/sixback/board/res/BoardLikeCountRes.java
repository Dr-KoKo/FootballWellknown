package com.a203.sixback.board.res;

import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardLikeCountRes extends BaseResponseBody {
    int count;

    public static BoardLikeCountRes of(Integer statusCode, String message, int count) {
        BoardLikeCountRes res = new BoardLikeCountRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setCount(count);
        return res;
    }
}
