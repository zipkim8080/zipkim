package com.kb.zipkim.domain.login.service;

import com.kb.zipkim.domain.login.dto.*;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final AccessToken tokenService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String accessToken = userRequest.getAccessToken().getTokenValue();
        System.out.println("액세스 토큰: " + accessToken);

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

        String username = oAuth2Response.getProviderId();
        String email = oAuth2Response.getEmail();
        String name = oAuth2Response.getName();
        System.out.println(username);
        tokenService.setAccessToken(username);

//        String user = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
//        System.out.println("user: " + user);

        UserEntity existData = userRepository.findByUsername(username);

        if (existData == null) {
            UserEntity userEntity = UserEntity.builder()
                    .username(username)
                    .email(email)
                    .name(name)
                    .role("ROLE_USER")
                    .build();
//            UserEntity userEntity = new UserEntity();
//            userEntity.setUsername(username);
//            userEntity.setEmail(email);
//            userEntity.setName(name);
//            userEntity.setRole("ROLE_USER");

            userRepository.save(userEntity);

            System.out.println(userRepository);

            UserDTO userDTO = UserDTO.builder()
                    .username(username)
                    .email(email)
                    .name(name)
                    .role("ROLE_USER")
                    .build();

//            UserDTO userDTO = new UserDTO();
//            userDTO.setUsername(username);
//            userDTO.setEmail(oAuth2Response.getEmail());
//            userDTO.setName(oAuth2Response.getName());
//            userDTO.setRole("ROLE_USER");

            Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new CustomOAuth2User(userDTO);

        }else
        {

            existData.setUsername(username);
            existData.setEmail(email);
            existData.setName(name);

            userRepository.save(existData);

            UserDTO userDTO = UserDTO.builder()
                    .username(existData.getUsername())
                    .email(email)
                    .name(name)
                    .build();

//            UserDTO userDTO = new UserDTO();
//            userDTO.setUsername(existData.getUsername());
//            userDTO.setEmail(oAuth2Response.getEmail());
//            userDTO.setName(oAuth2Response.getName());
//            userDTO.setRole(existData.getRole());

            Authentication authentication = new UsernamePasswordAuthenticationToken(existData, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new CustomOAuth2User(userDTO);

        }

    }

}
