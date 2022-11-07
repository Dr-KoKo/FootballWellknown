package com.a203.sixback.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBoardReqDTO {
    private Long boardId;
    private String title;
    private String ctgName;
    private String content;
    private int temaId;
    private Long matchId;
}
