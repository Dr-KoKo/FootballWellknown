package com.a203.sixback.board.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentResDTO {
    String author;
    String comment;
}
