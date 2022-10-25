package com.a203.sixback.board.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardDetailResDTO {
    private long id;
    private String title;
    private String ctgName;
    private String content;
    private long author;
    private LocalDateTime createDate;
    private List<GetCommentResDTO> comments;

}
