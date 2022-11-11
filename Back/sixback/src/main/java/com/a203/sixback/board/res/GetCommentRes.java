package com.a203.sixback.board.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentRes {
    int commentId;
    String author;
    String comment;
    LocalDateTime createDate;
}
