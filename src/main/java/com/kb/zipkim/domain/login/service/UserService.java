package com.kb.zipkim.domain.login.service;

import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String getPhoneNumber(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return user.getPhonenumber();
    }

    public String getBrokerNumber(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return user.getBrokerNo();
    }

    public void addPhoneNumber(String username, String phoneNumber, String brokerNumber) {
        UserEntity user = userRepository.findByUsername(username);
        user.setPhonenumber(phoneNumber);
        user.setBrokerNo(brokerNumber);
        user.setRole("ROLE_BROKER");
        userRepository.save(user);
    }

    public String getRole(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return user.getRole();
    }
}