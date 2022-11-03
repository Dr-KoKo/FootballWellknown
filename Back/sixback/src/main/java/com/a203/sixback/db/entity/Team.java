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

    @Column
    private int win;
    @Column
    private int draw;
    @Column
    private int lose;
    @Column
    private int goals;
    @Column
    private int loseGoals;
    @OneToMany(mappedBy = "team")
    private List<Player> players;
    @OneToOne(mappedBy = "team")
    private Coach coach;
    public void setTeamInfo(int[] arr){
        this.win = arr[0];
        this.draw = arr[1];
        this.lose = arr[2];
        this.goals = arr[3];
        this.loseGoals = arr[4];
    }
    public void addTeamInfo(int myScore, int otherScore){
        if(myScore>otherScore){
            this.win = this.win +1;
        }
        else if(myScore==otherScore){
            this.draw = this.draw+1;
        }
        else{
            this.lose = this.lose+1;
        }
        this.goals = this.goals + myScore;
        this.loseGoals = this.loseGoals + otherScore;
    }
}
