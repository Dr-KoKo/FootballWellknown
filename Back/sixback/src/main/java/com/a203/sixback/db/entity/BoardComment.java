package com.a203.sixback.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "boardComment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardComment {
    @Id
    private String id;
    private Long boardId;
    private List<Comment> comments;
}
