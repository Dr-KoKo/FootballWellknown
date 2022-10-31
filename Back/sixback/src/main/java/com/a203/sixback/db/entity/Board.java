package com.a203.sixback.db.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BOARD")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Data
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ctg_id")
    private Category category;

    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "autor")
    private User user;

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id")
    private Matches match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
