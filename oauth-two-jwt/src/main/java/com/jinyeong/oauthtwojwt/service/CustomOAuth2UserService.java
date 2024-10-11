package com.jinyeong.oauthtwojwt.service;

import com.jinyeong.oauthtwojwt.domain.*;
import com.jinyeong.oauthtwojwt.entity.UserEntity;
import com.jinyeong.oauthtwojwt.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // 리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        String username = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
        UserEntity savedUser = userRepository.findByUsername(username);
        if (savedUser == null) {
            // Save
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setEmail(oAuth2Response.getEmail());
            userEntity.setName(oAuth2Response.getName());
            userEntity.setRole("ROLE_USER");
            userRepository.save(userEntity);

            // User 객체 반환
            User user = new User();
            user.setUsername(username);
            user.setName(oAuth2Response.getName());
            user.setRole("ROLE_USER");
            return new CustomOAuth2User(user);
        } else {
            // Update
            savedUser.setEmail(oAuth2Response.getEmail());
            savedUser.setName(oAuth2Response.getName());
            userRepository.save(savedUser);

            // User 객체 반환
            User user = new User();
            user.setUsername(savedUser.getUsername());
            user.setRole(savedUser.getRole());
            user.setName(oAuth2Response.getName());
            return new CustomOAuth2User(user);
        }


    }
}