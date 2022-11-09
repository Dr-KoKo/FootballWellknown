package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Matches;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchesCustomRepo {
    List<Matches> findAllByYearAndMonthOrderByMatch_Date(@Param("year") int year, @Param("month") int month);
}
