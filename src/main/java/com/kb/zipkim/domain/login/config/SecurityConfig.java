package com.kb.zipkim.domain.login.config;

import com.kb.zipkim.domain.login.jwt.JWTFilter;
import com.kb.zipkim.domain.login.jwt.JWTUtil;
import com.kb.zipkim.domain.login.oauth2.CustomAuthenticationEntryPoint;
import com.kb.zipkim.domain.login.oauth2.CustomSuccessHandler;
import com.kb.zipkim.domain.login.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomSuccessHandler customSuccessHandler, JWTUtil jwtUtil) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) throws Exception {

        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "https://production.d19ghcfaxxas13.amplifyapp.com"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                }));


        http
                .csrf(csrf -> csrf.disable())    //csrf disable
                .formLogin(login -> login.disable())   //From 로그인 방식 disable
                .httpBasic(basic -> basic.disable())   //HTTP Basic 인증 방식 disable
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)   // JWTFilter 추가
                .oauth2Login((oauth2) -> oauth2     //oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler))
                .authorizeHttpRequests((auth) -> auth       //경로별 인가 작업
//                       .requestMatchers("/").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/property").authenticated()
                        .requestMatchers("/api/bookmark/*").authenticated()
                        .requestMatchers("/api/myprops/*").authenticated()
                        .requestMatchers("/api/reissue").permitAll()
                       .anyRequest().permitAll())
//                         .anyRequest().authenticated())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(customAuthenticationEntryPoint))
                .sessionManagement((session) -> session     //세션 설정 : STATELESS
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
