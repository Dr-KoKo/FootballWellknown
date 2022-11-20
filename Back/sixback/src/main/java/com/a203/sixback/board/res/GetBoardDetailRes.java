package com.a203.sixback.board.res;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardDetailRes {
    private long id;
    private String title;
    private String ctgName;
    private String content;
    private String author;
    private Integer team;
    private Long match;
    private LocalDateTime createDate;
    private List<GetCommentRes> comments;

}
