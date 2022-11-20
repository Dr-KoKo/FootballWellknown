package com.a203.sixback.board.res;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardRes {
    long id;
    String title;
    String author;
}
