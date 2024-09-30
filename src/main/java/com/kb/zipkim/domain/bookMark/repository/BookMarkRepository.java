package com.kb.zipkim.domain.bookMark.repository;

import com.kb.zipkim.domain.bookMark.entity.BookMark;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.prop.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.error.Mark;

import java.util.List;
import java.util.Optional;

public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    List<BookMark> findByUsername(String username);
    BookMark findByUsernameAndPhonenumber(String username, String phonenumber);
}
