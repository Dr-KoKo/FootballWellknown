package com.a203.sixback.auth;

import com.a203.sixback.db.entity.User;
import com.a203.sixback.db.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public User getUser(String userEmail) {
        return userRepo.findByEmail(userEmail);
    }
}
