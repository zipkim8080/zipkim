package com.kb.zipkim.domain.login.repository;

import com.kb.zipkim.domain.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
}
