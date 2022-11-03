package com.a203.sixback.db.entity;

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
public class MatchPredict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;
    @ManyToOne
    @JoinColumn(name = "matches_id")
    private Matches matches;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private String whereWin;

    public MatchPredict(User user, Matches matches, String whereWin) {
        this.user = user;
        this.matches = matches;
        this.whereWin = whereWin;
    }

    public void setWhereWin(String whereWin) {
        this.whereWin = whereWin;
    }
}
