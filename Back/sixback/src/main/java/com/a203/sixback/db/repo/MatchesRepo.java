package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchesRepo extends JpaRepository<Matches, Long> {
    @Query(nativeQuery = true, value="select id from matches where match_status='FIN';")
    List<Long> findAllByStatus();
    @Query(nativeQuery = true, value="select * from matches where (home=:teamId or away=:teamId) and match_status='FIN' order by match_date desc LIMIT 3;")
    List<Matches> findRecentFINMatches(@Param("teamId") int teamId);

    @Query(nativeQuery = true, value="select * from matches where (home=:teamId or away=:teamId) and match_status='READY' order by match_date LIMIT 3;")
    List<Matches> findRecentREADYMatches(@Param("teamId") int teamId);

    List<Matches> findAllByRound(int round);
    @Query(nativeQuery = true, value="select * from matches where year(match_date)=:year and month(match_date)=:month ;")
    List<Matches> findAllByYearAndMonth(@Param("year") int year, @Param("month") int month);

    @Query(nativeQuery = true, value="select * from matches where year(match_date)=:year and month(match_date)=:month and day(match_date)=:day ;")
    List<Matches> findAllByYearAndMonthAndDay(@Param("year") int year, @Param("month") int month, @Param("day") int day);
}
