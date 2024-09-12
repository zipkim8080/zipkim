package com.kb.zipkim.domain.prop.repository;

import com.kb.zipkim.domain.prop.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findByBrokerId(Long brokerId);
    List<Property> findByComplexId(Long complexId);
}
