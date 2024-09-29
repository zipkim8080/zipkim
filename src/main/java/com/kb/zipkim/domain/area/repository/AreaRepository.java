package com.kb.zipkim.domain.area.repository;

import com.kb.zipkim.domain.area.entity.Area;
import com.kb.zipkim.domain.complex.entity.Complex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByComplex(Complex complex);
}
