package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Matches;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.a203.sixback.db.entity.QMatches.matches;
import static com.a203.sixback.db.entity.QTeam.team;

@Repository
public class MatchesCustomRepoImpl implements MatchesCustomRepo{

    private final JPAQueryFactory jpaQueryFactory;

    public MatchesCustomRepoImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Matches> findAllByYearAndMonthOrderByMatch_Date(int year, int month) {
        return jpaQueryFactory.selectFrom(matches)
                .where(matches.matchDate.month().eq(month)
                        .and(matches.matchDate.year().eq(year)))
                .orderBy(matches.matchDate.asc())
                .fetch();
    }
}

