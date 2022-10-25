package com.a203.sixback.user;

import com.a203.sixback.auth.UserPrincipal;
import com.a203.sixback.db.entity.User;
import com.a203.sixback.db.repo.UserRepo;
import com.a203.sixback.user.res.ResGetUserDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public ResGetUserDetailsDTO getUserDetails() {
        ResGetUserDetailsDTO result = new ResGetUserDetailsDTO();

        String email = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userRepo.findByEmail(email);

        return ResGetUserDetailsDTO.of(200,"성공", user);
    }

    public User getUser(String userEmail) {
        String email = ((UserPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();

        return null;
    }


}
