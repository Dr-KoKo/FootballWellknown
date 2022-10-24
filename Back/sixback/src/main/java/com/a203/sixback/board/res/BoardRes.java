package com.a203.sixback.board.res;

import com.a203.sixback.board.dto.GetBoardResDTO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardRes extends BaseResponseBody {
    List<GetBoardResDTO> boardResult;

    public static BoardRes of(Integer statusCode, String message, List<GetBoardResDTO> boardResult) {
        BoardRes res = new BoardRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setBoardResult(boardResult);
        return res;
    }
}
