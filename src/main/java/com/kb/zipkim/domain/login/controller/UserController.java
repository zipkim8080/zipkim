package com.kb.zipkim.domain.login.controller;

import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{username}") //이름 검색할때
    public String getUserName(@PathVariable String username) {
        //다른 필터로 바꾸고 싶을경우 username을 변경
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("유저가 없습니다.");
            return "User not found";
        } else {
            System.out.println(user.getName());
            return user.getName();
        }
    }
}
