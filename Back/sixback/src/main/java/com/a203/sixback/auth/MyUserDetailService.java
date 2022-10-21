package com.a203.sixback.auth;

import com.a203.sixback.db.entity.User;
import com.a203.sixback.db.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    private static Logger LOGGER = LoggerFactory.getLogger(MyUserDetailService.class);

    private final UserRepo userRepo;

    public MyUserDetailService(UserRepo userRepo) { this.userRepo = userRepo;}
    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepo.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User ID : " + username + " Not Found")
        );
        return UserPrincipal.create(user);
    }
}
