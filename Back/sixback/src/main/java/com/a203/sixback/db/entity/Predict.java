package com.a203.sixback.db.entity;

import com.a203.sixback.db.enums.MatchResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private Matches matches;

    @Enumerated(EnumType.STRING)
    private MatchResult matchResult;

    public Predict(User user, Matches matches, MatchResult matchResult) {
        this.user = user;
        this.matches = matches;
        this.matchResult = matchResult;
    }

}
