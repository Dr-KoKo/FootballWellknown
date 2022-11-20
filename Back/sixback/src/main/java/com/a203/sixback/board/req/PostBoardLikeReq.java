package com.a203.sixback.board.req;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class PostBoardLikeReq {
    private Long boardId;
    private boolean checkLiked;
}
