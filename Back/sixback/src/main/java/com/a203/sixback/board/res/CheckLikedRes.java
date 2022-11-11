package com.a203.sixback.board.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckLikedRes {
    private boolean isLiked;
    private int numLiked;
}
