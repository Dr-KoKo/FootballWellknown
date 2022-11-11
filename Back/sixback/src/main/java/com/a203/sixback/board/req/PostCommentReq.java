package com.a203.sixback.board.req;

import lombok.Data;

@Data
public class PostCommentReq {
    Long boardId;
    String comment;
}
