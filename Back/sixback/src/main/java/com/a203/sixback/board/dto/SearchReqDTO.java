package com.a203.sixback.board.dto;

import lombok.Data;

@Data
public class SearchReqDTO {
    Long currentPage;
    String type;
    String keyword;
}
