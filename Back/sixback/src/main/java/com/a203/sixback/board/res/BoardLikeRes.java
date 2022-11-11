package com.a203.sixback.board.res;

import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Data;

@Data
public class BoardLikeRes extends BaseResponseBody {
    CheckLikedRes checkLiked;

    public static BoardLikeRes of(Integer statusCode, String message, CheckLikedRes checkLiked) {
        BoardLikeRes res = new BoardLikeRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setCheckLiked(checkLiked);
        return res;
    }
}
