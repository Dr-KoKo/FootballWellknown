package com.a203.sixback.board.res;

import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardDetailListRes  extends BaseResponseBody {
    List<GetBoardDetailRes> boardList;
    public static BoardDetailListRes of(Integer statusCode, String message, List<GetBoardDetailRes> boardList) {
        BoardDetailListRes res = new BoardDetailListRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setBoardList(boardList);
        return res;
    }
}