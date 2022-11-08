package com.a203.sixback.db.entity;

import com.a203.sixback.db.enums.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Matches {
    @Id
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;
    @Column
    private LocalDateTime matchDate;
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;
    @Column
    private int homeScore;
    @Column
    private int awayScore;
    @Column
    private String stadium;
    @Column
    private String referee;
    @Column
    private int round;
    @ManyToOne
    @JoinColumn(name = "home")
    private Team home;
    @OneToMany(mappedBy = "matches")
    private List<MatchHistory> matchHistoryList;
    @OneToMany(mappedBy = "matches")
    private List<PlayerMatch> playerMatchList;
    @OneToMany(mappedBy = "matches")
    private List<MatchDet> matchDetList;
    @ManyToOne
    @JoinColumn(name = "away")
    private Team away;

    public void setScore(int homeScore, int awayScore){
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.matchStatus = MatchStatus.FIN;
    }
}
