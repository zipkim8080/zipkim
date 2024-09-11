package com.kb.zipkim.domain.prop.repository;

import com.kb.zipkim.domain.prop.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {

}
