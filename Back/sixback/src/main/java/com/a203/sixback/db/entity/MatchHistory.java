package com.a203.sixback.db.entity;

import com.a203.sixback.db.enums.History;
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
public class MatchHistory {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;
    @ManyToOne
    @JoinColumn(name = "matches_id")
    private Matches matches;

    @Column
    private String time;
    @Enumerated(EnumType.STRING)
    private History history;
    @Enumerated(EnumType.STRING)
    private TeamType teamType;
    @Column
    private String mainName;
    @Column
    private String subName;
    @Column
    private String info;
}
