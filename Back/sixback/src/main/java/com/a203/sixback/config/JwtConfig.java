package com.a203.sixback.config;

import com.a203.sixback.auth.AuthTokenProvider;
import com.a203.sixback.db.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UserRepo userRepo;

    @Bean
    public AuthTokenProvider jwtProvider() {
        return new AuthTokenProvider(secret, userRepo);
    }
}
