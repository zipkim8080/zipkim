package com.kb.zipkim.domain.prop.repository;

import com.kb.zipkim.domain.prop.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

//    List<Property> findByBrokerId(Long brokerId);
    Page<Property> findByBrokerId(Long brokerId, Pageable pageable);

    @Query(value = "select p from Property p " +
            "join fetch p.registered r " +
            "join fetch p.complex c "+
            "where p.complex.id = :complexId ",
            countQuery = "select count(p.id) from Property p where p.complex.id =:complexId")
    Page<Property> findByComplexId(@Param("complexId") Long complexId, Pageable pageable);
}
