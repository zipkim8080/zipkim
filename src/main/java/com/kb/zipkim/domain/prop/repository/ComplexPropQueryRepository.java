package com.kb.zipkim.domain.prop.repository;

import com.kb.zipkim.domain.prop.dto.SearchResponse;
import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.prop.entity.QProperty;
import com.kb.zipkim.domain.prop.file.QUploadFile;
import com.kb.zipkim.domain.register.entity.QRegistered;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.kb.zipkim.domain.prop.entity.QComplex.*;
import static com.kb.zipkim.domain.prop.entity.QProperty.*;
import static com.kb.zipkim.domain.prop.file.QUploadFile.*;
import static com.kb.zipkim.domain.register.entity.QRegistered.*;

@Repository
@RequiredArgsConstructor
public class ComplexPropQueryRepository {

    private final JPAQueryFactory queryFactory;

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
                .join(property.registered, registered).fetchJoin()
                .join(property.images, uploadFile).fetchJoin()
                .where(property.id.eq(id))
                .fetchOne();

    }



}
