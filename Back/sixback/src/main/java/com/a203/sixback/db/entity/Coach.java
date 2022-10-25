package com.a203.sixback.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "INT")
    private int id;
    @Column(name="name")
    private String name;
    @Column
    private String image;
    @Column(name="age")
    private int age;
    @Column
    private String country;
    @OneToOne
    @JoinColumn(name="team_id")
    private Team team;
}
