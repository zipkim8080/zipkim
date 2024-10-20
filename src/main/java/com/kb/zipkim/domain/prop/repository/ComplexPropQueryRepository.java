package com.kb.zipkim.domain.prop.repository;

import com.kb.zipkim.domain.complex.dto.SearchResponse;
import com.kb.zipkim.domain.complex.entity.Complex;
import com.kb.zipkim.domain.login.entity.QUserEntity;
import com.kb.zipkim.domain.prop.entity.Property;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kb.zipkim.domain.complex.entity.QComplex.*;
import static com.kb.zipkim.domain.login.entity.QUserEntity.*;
import static com.kb.zipkim.domain.prop.entity.QProperty.*;
import static com.kb.zipkim.domain.prop.file.QUploadFile.*;
import static com.kb.zipkim.domain.register.entity.QRegistered.*;

@Repository
@RequiredArgsConstructor
public class ComplexPropQueryRepository {

    private final JPAQueryFactory queryFactory;

    @PersistenceContext
    private EntityManager em;

    public Page<SearchResponse> search(String keyword, Pageable pageable) {
        List<SearchResponse> content = queryFactory.select(Projections.constructor(SearchResponse.class,
                        complex.id,
                        complex.complexName,
                        complex.type,
                        complex.addressName,
                        complex.latitude,
                        complex.longitude
                ))
                .from(complex)
                .where(complex.complexName.contains(keyword).or(complex.addressName.contains(keyword)))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = queryFactory.select(complex.count())
                .from(complex)
                .where(complex.complexName.contains(keyword).or(complex.addressName.contains(keyword)));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    public Property findByIdWithRegisterAndImages(@Param("id") Long id){
        return queryFactory.select(property)
                .from(property)
                .leftJoin(property.registered, registered).fetchJoin()
                .leftJoin(property.images, uploadFile).fetchJoin()
                .join(property.complex, complex).fetchJoin()
                .join(property.broker, userEntity).fetchJoin()
                .where(property.id.eq(id))
                .fetchOne();

    }

    //테스트메서드
    public List<Complex> findComplexesWithin10km(double latitude, double longitude) {
        String query = "SELECT c.*, "
                + "(6371 * ACOS(COS(RADIANS(:latitude)) "
                + "* COS(RADIANS(c.latitude)) "
                + "* COS(RADIANS(c.longitude) - RADIANS(:longitude)) "
                + "+ SIN(RADIANS(:latitude)) "
                + "* SIN(RADIANS(c.latitude)))) AS distance "
                + "FROM complex c "
                + "HAVING distance <= 10 "
                + "ORDER BY distance";

        return em.createNativeQuery(query, Complex.class)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .getResultList();
    }



}
