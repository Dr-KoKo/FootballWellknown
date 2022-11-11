package com.a203.sixback.board.res;

import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardRes extends BaseResponseBody {
    List<GetBoardRes> boardList;
    int lastPage;
    public static BoardRes of(Integer statusCode, String message, List<GetBoardRes> boardList, int lastPage) {
        BoardRes res = new BoardRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setBoardList(boardList);
        res.setLastPage(lastPage);
        return res;
    }
}
