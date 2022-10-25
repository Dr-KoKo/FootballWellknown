package com.a203.sixback.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostBoardReqDTO {
    private String title;
    private String ctgName;
    private String content;
    private Long matchId;
}
