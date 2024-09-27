package com.kb.zipkim.domain.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String role;
    private String name;
    private String email;
    private String username;

    public void addUsername(String username) { this.username = username; }
    public void addRole(String role) {
        this.role = role;
    }
    public void addName(String name) {
        this.name = name;
    }
    public void addEmail(String email) {
        this.email = email;
    }
}
