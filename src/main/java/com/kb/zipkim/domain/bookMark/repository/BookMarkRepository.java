package com.kb.zipkim.domain.bookMark.repository;

import com.kb.zipkim.domain.bookMark.entity.BookMark;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.prop.entity.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    Optional<BookMark> findByUserAndProperty(UserEntity user, Property property);

    @Query("SELECT b FROM BookMark b " +
            "JOIN fetch b.property p " +
            "Join fetch p.complex c " +
            "WHERE b.user = :user")
    Page<BookMark> findByUser(UserEntity user, Pageable pageable);
//    BookMark findByUserAndProbid(UserEntity user, String probid);
//    Optional<BookMark> findByIdAndUser(Long id, UserEntity user);

    Optional<BookMark> findByProperty(Property property);

    void deleteByProperty(Property property);
}
