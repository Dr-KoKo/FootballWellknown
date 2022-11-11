package com.a203.sixback.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "boardLike")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardLike {
    @Id
    private String id;
    private Long boardId;
    private List<Long> people;
}
