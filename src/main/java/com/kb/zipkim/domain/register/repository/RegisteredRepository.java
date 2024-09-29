package com.kb.zipkim.domain.register.repository;

import com.kb.zipkim.domain.register.entity.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisteredRepository extends JpaRepository<Registered, String> {
    boolean existsByUniqueNumber(String uniqueNumber);
}
