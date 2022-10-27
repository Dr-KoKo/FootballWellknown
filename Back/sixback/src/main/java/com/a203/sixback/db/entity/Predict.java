package com.a203.sixback.db.entity;

import com.a203.sixback.db.enums.MatchResult;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Predict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(20) UNSIGNED")
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Matches.class)
    @JoinColumn(name = "match_id")
    private Matches match;

    @Enumerated(EnumType.STRING)
    private MatchResult matchResult;

}
