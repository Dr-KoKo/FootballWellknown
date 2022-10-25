package com.a203.sixback.board.res;

import com.a203.sixback.board.dto.GetCommentResDTO;
import com.a203.sixback.util.model.BaseResponseBody;
import lombok.Data;

import java.util.List;

@Data
public class CommentRes extends BaseResponseBody {
    List<GetCommentResDTO> commentResult;

    public static CommentRes of(Integer statusCode, String message, List<GetCommentResDTO> commentResult) {
        CommentRes res = new CommentRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setCommentResult(commentResult);
        return res;
    }
}
