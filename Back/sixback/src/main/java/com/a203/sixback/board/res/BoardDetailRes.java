package com.a203.sixback.board.res;

import com.a203.sixback.board.dto.GetBoardDetailResDTO;
import com.a203.sixback.board.dto.GetBoardResDTO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BoardDetailRes extends BaseResponseBody {
    GetBoardDetailResDTO boardDetail;

    public static BoardDetailRes of(Integer statusCode, String message, GetBoardDetailResDTO boardDetail) {
        BoardDetailRes res = new BoardDetailRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setBoardDetail(boardDetail);
        return res;
    }
}
