package com.a203.sixback.db.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;

    @Column
    private int userRate;
    @Column
    private int userCount;
    @Column
    private int expertRate;
    @Column
    private int goal;
    @Column
    private int assist;
    @Column
    private int position;
    @Column
    private int shot;
    @Column
    private int shotOn;
    @Column
    private int pass;
    @Column
    private int passOn;
    @Column
    private int foul;
    @Column
    private int crossed;
    @Column
    private int crossedOn;
    @Column
    private int dribble;
    @Column
    private int dribbleOn;
    @Column
    private int tackle;
    @Column
    private int clear;
    @Column
    private int yellow;
    @Column
    private int red;
    @Column
    private int sub;
    @ManyToOne
    @JoinColumn(name = "matches_id")
    private Matches matches;

    @ManyToOne
    @JoinColumn(name="player_id")
    private Player player;

}
