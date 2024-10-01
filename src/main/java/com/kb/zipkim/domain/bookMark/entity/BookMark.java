package com.kb.zipkim.domain.bookMark.entity;

import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.prop.entity.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.print.Book;

@Entity
@Getter
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prop_id")
    private Property property;

}
