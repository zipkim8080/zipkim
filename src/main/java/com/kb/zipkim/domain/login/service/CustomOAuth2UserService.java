package com.kb.zipkim.domain.login.service;

import com.kb.zipkim.domain.login.dto.*;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final AccessToken tokenService;

    public UserEntity getUserByEmail(String username) {
        return userRepository.findByUsername(username);
    }
    public CustomOAuth2UserService(UserRepository userRepository, AccessToken tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());
        System.out.println("success");

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String accessToken = userRequest.getAccessToken().getTokenValue();
        System.out.println("액세스 토큰: " + accessToken);

//        tokenService.setAccessToken(accessToken);

        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        } else if (registrationId.equals("kakao")) {

            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());

        } else {
            return null;
        }

        String username = oAuth2Response.getProvider();
        System.out.println(username);
        tokenService.setAccessToken(username);

        UserEntity existData = userRepository.findByUsername(username);

        if (existData == null)
        {

            UserEntity userEntity = new UserEntity();
            userEntity.addUsername(username);
            userEntity.addEmail(oAuth2Response.getEmail());
            userEntity.addName(oAuth2Response.getName());
            userEntity.addRole("ROLE_USER");

            userRepository.save(userEntity);

            UserDTO userDTO = new UserDTO();
            userDTO.addUsername(username);
            userDTO.addEmail(oAuth2Response.getEmail());
            userDTO.addName(oAuth2Response.getName());
            userDTO.addRole("ROLE_USER");

            Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new CustomOAuth2User(userDTO);

        }else
        {

            existData.setUsername(username);
            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            userRepository.save(existData);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(existData.getUsername());
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(existData.getRole());

            Authentication authentication = new UsernamePasswordAuthenticationToken(existData, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("유저 이름입니당 : " + userDTO.getUsername());
            return new CustomOAuth2User(userDTO);

        }

    }

}
