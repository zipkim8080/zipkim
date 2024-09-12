package com.kb.zipkim.domain.prop.repository;

import com.kb.zipkim.domain.prop.entity.Complex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComplexRepository extends JpaRepository<Complex, Long> {

    Optional<Complex> findByDongAndMainAddressNoAndSubAddressNo(String dong, String mainAddressNo, String subAddressNo);
}
