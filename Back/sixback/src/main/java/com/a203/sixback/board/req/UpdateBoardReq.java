package com.a203.sixback.board.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBoardReq {
    private Long boardId;
    private String title;
    private String ctgName;
    private String content;
    private int temaId;
    private Long matchId;
}
