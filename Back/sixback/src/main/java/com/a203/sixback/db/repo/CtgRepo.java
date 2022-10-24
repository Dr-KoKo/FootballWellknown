package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CtgRepo extends JpaRepository<Category, Long> {
    Category findByCtgName(String ctgName);
}
