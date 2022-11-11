package com.a203.sixback.board.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostBoardReq {
    private String title;
    private String ctgName;
    private String content;
    private Long matchId;
    private Integer teamId;
}
