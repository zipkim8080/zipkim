package com.kb.zipkim.domain.prop.repository;

import com.kb.zipkim.domain.login.config.SecurityConfig;
import com.kb.zipkim.domain.login.jwt.JWTUtil;
import com.kb.zipkim.domain.prop.entity.Complex;
import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.prop.entity.QComplex;
import com.kb.zipkim.domain.register.Registered;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.kb.zipkim.domain.prop.entity.QComplex.*;

@SpringBootTest
class PropertyRepositoryTest {

    @MockBean
    JWTUtil jwtUtil;
    @MockBean
    SecurityConfig securityConfig;

    @Autowired
    EntityManager em;
    @Autowired
    PropertyRepository propertyRepository;

    @Test
    public void 매물등록테스트() throws Exception{

    }
}