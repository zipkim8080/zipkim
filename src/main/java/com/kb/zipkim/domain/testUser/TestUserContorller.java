package com.kb.zipkim.domain.testUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class TestUserContorller {
    private final TestUserMapper testUserMapper;

    @GetMapping("/test_user")
    public List<TestUser> getAllTestUser() {
        return testUserMapper.findAll();
    }
}
