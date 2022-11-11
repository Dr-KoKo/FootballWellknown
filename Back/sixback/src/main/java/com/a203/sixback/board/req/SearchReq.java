package com.a203.sixback.board.req;

import lombok.Data;

@Data
public class SearchReq {
    Long currentPage;
    String type;
    String keyword;
}
