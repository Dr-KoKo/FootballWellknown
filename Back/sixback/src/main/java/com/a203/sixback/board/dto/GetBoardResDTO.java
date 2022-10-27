package com.a203.sixback.board.dto;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardResDTO {
    long id;
    String title;
    String author;
}
