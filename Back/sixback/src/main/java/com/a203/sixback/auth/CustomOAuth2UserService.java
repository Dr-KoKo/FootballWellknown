package com.a203.sixback.auth;

import com.a203.sixback.db.enums.ProviderType;
import com.a203.sixback.db.entity.User;
import com.a203.sixback.db.enums.RoleType;
import com.a203.sixback.db.enums.Status;
import com.a203.sixback.db.repo.UserRepo;
import com.a203.sixback.exception.OAuthProviderMissMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepo userRepo;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("LOADUSER");
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        User savedUser = userRepo.findByEmail(userInfo.getEmail());

        if (savedUser != null) {
            if (providerType != savedUser.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                                " account. Please use your " + savedUser.getProviderType() + " account to login."
                );
            }
        } else {
            savedUser = createUser(userInfo, providerType);
        }

        return UserPrincipal.create(savedUser, user.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {


        String nickname;

        do {
            nickname = providerType.toString().substring(0, 3) + RandomStringUtils.randomNumeric(10);
        } while (userRepo.existsByNickname(nickname));

        User user = User.builder()
                .nickname(nickname)
                .point(0)
                .email(userInfo.getEmail())
                .providerType(providerType)
                .roleType(RoleType.USER)
                .status(Status.ACTIVATED)
                .build();

        return userRepo.saveAndFlush(user);
    }

}
