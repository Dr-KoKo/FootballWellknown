package com.a203.sixback.db.entity;

import com.a203.sixback.db.enums.TeamType;
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
public class MatchDet {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;
    @Column
    private int shot;
    @Column
    private int shotOn;
    @Column
    private int foul;
    @Column
    private int corner;
    @Column
    private int penalty;
    @Column
    private int offside;
    @Column
    private String possession;
    @Column
    private String formation;
    @Column
    private int yellow;
    @Column
    private int red;
    @Column
    private int save;
    @Column
    private int pass;
    @Column
    private int passOn;
    @Enumerated(EnumType.STRING)
    private TeamType teamType;
    @ManyToOne
    @JoinColumn(name = "matches_id")
    private Matches matches;

    public void setPassOn(int passOn){
        this.passOn = passOn;
    }
}
