package com.kb.zipkim.domain.login.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    private String email;

    private String role;

    public void addUsername(String username) {
        this.username = username;
    }

    public void addRole(String role) {
        this.role = role;
    }

    public void addEmail(String email) {
        this.email = email;
    }

    public void addName(String name) {
        this.name = name;
    }

    public void addid(Long id) {
        this.id = id;
    }

}
