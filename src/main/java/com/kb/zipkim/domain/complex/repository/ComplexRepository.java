package com.kb.zipkim.domain.complex.repository;

import com.kb.zipkim.domain.complex.entity.Complex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComplexRepository extends JpaRepository<Complex, Long> {

    Optional<Complex> findByBgdCdAndMainAddressNoAndSubAddressNo(String bgdCd, String mainAddressNo, String subAddressNo);
    List<Complex> findByBgdCd(String bgdCd);
}
