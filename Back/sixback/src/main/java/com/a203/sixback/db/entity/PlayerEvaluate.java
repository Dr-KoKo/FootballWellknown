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
public class PlayerEvaluate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;
    @ManyToOne
    @JoinColumn(name = "matches_id")
    private Matches matches;
//    @ManyToOne
//    @JoinColumn(name = "id")
//    private User user;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    @Column
    private int score;

}
