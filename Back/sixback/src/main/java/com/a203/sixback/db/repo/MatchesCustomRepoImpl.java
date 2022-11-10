package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Matches;
import com.a203.sixback.db.entity.QTeam;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.a203.sixback.db.entity.QMatches.matches;
import static com.a203.sixback.db.entity.QTeam.team;
import static com.a203.sixback.db.entity.QCoach.coach;

@Repository
public class MatchesCustomRepoImpl implements MatchesCustomRepo{

    private final JPAQueryFactory jpaQueryFactory;

    public MatchesCustomRepoImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Matches> findAllByYearAndMonthOrderByMatch_Date(int year, int month) {
        QTeam home = QTeam.hometeam;
        QTeam away = QTeam.awayteam;
        return jpaQueryFactory.select(matches).from(matches)

                .leftJoin(matches.home,home).fetchJoin()
                .innerJoin(home.coach,coach).fetchJoin()
                .leftJoin(matches.away,away).fetchJoin()
                .innerJoin(away.coach,coach).fetchJoin()
                .where(matches.matchDate.month().eq(month)
                        .and(matches.matchDate.year().eq(year)))
                .orderBy(matches.matchDate.asc())
                .fetch();
    }

    @Override
    public List<Matches> findAllByRound(int round) {
        QTeam home = QTeam.hometeam;
        QTeam away = QTeam.awayteam;
        return jpaQueryFactory.select(matches).from(matches)

                .leftJoin(matches.home,home).fetchJoin()
                .innerJoin(home.coach,coach).fetchJoin()
                .leftJoin(matches.away,away).fetchJoin()
                .innerJoin(away.coach,coach).fetchJoin()
                .where(matches.round.eq(round))
                .orderBy(matches.matchDate.asc())
                .fetch();
    }

    @Override
    public Matches findById(long id) {
        QTeam home = QTeam.hometeam;
        QTeam away = QTeam.awayteam;
        return jpaQueryFactory.select(matches).from(matches)

                .leftJoin(matches.home,home).fetchJoin()
                .innerJoin(home.coach,coach).fetchJoin()
                .leftJoin(matches.away,away).fetchJoin()
                .innerJoin(away.coach,coach).fetchJoin()
                .where(matches.id.eq(id))
                .orderBy(matches.matchDate.asc())
                .fetchOne();
    }
}

