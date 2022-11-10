package com.a203.sixback.db.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDateTime;


@Document(collection = "comment")
@Builder
@Data
public class Comment {
    private Integer id;
    private String comment;
    private String author;
    private Long authorId;
    private LocalDateTime createDate;
}
