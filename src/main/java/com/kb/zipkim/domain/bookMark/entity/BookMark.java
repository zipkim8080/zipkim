package com.kb.zipkim.domain.bookMark.entity;

import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.prop.entity.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.print.Book;

@Entity
@Getter
@Setter
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String probid;

    private String deposit;

    private String amount;

    private String floor;

    private String image;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prop_id")
    private Property property;*/

}
