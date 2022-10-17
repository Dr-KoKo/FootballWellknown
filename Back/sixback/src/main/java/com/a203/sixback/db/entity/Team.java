package com.a203.sixback.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @Column(name="id", columnDefinition = "INT")
    private int id;

    @Column(name="name", length = 45)
    private String name;

    @Column(name="image")
    private String image;

    @OneToMany(mappedBy = "team")
    private List<Player> players;

}
