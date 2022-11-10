package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.QTeam;
import com.a203.sixback.db.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TeamCustomRepoImpl implements TeamCustomRepo{
    private final JPAQueryFactory jpaQueryFactory;
    public TeamCustomRepoImpl(JPAQueryFactory jpaQueryFactory){this.jpaQueryFactory = jpaQueryFactory;}
    QTeam team = QTeam.team;
    @Override
    public List<Team> findAll() {
        return jpaQueryFactory.select(team)
                .from(team)
                .fetch();
    }
}
