package com.a203.sixback.db.repo;

import com.a203.sixback.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByNickname(String nickname);
}
