package com.a203.sixback.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    @Id
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;

    @Column(name="name", length = 45)
    private String name;

    @Column(name="image")
    private String image;

    @Column(name="birth")
    private String birth;

    @Column
    private int height;

    @Column
    private int weight;
    @Column
    private String history;

    @Column
    private int goals;
    @Column
    private int assists;
    @Column
    private String country;
    @Column
    private Integer number;
    @Column
    private int joinMatches;
    @Column
    private String position;
    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;
    @OneToMany(mappedBy = "player")
    private List<PlayerMatch> playerMatchList;
}
