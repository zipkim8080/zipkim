package com.kb.zipkim.domain.login.service;

import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addPhoneNumber(String username, String phoneNumber) {
        UserEntity user = userRepository.findByUsername(username);
        user.setPhonenumber(phoneNumber);
        userRepository.save(user);
    }
}
