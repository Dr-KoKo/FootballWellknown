package com.a203.sixback.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentResDTO {
    int commentId;
    String author;
    String comment;
    LocalDateTime createDate;
}
