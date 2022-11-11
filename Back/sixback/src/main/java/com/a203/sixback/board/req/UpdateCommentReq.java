package com.a203.sixback.board.req;

import lombok.Data;

@Data
public class UpdateCommentReq {
    String commentId;
    String comment;
}
