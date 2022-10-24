package com.a203.sixback.db.entity;

import com.a203.sixback.db.enums.ProviderType;
import com.a203.sixback.db.enums.RoleType;
import com.a203.sixback.db.enums.Status;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(20) UNSIGNED")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickname;

    @ColumnDefault("0")
    private Integer point;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) default 'ACTIVATED'")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20)")
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20) default 'ROLE_USER'")
    private RoleType roleType;

}
