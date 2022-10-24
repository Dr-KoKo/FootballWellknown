package com.a203.sixback.db.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
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
    private String aurtor;
    private LocalDateTime createDate;
}
