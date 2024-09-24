package com.kb.zipkim.domain.prop.repository;

import com.kb.zipkim.domain.login.config.SecurityConfig;
import com.kb.zipkim.domain.login.jwt.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

//@SpringBootTest
class PropertyRepositoryTest {

    @MockBean
    JWTUtil jwtUtil;
    @MockBean
    SecurityConfig securityConfig;

    @Test
    public void 매물등록테스트() throws Exception{

    }
}