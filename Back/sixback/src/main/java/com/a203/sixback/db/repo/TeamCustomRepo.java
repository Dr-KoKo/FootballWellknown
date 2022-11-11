package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Team;

import java.util.List;

public interface TeamCustomRepo {
    List<Team> findAll();
}
