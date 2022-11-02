package com.a203.sixback.db.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(20) UNSIGNED")
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pe_id")
    private PlayerEvaluate pe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "predict_id",columnDefinition = "BIGINT(20) UNSIGNED")
    private Predict mp;

    private Integer point;

    private LocalDateTime distribute_time;

}
