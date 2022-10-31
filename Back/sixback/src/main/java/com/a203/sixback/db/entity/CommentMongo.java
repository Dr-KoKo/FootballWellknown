package com.a203.sixback.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "comment")
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentMongo {
    @Id
    private String id;
    private Long boardId;
    private String comment;
    private String author;
    private Long author_id;
    private LocalDateTime createDate;
}
