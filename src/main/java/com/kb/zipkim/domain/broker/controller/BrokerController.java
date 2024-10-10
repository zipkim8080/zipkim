package com.kb.zipkim.domain.broker.controller;

import com.kb.zipkim.domain.broker.dto.BrokerDTO;
import com.kb.zipkim.domain.broker.service.BrokerService;
import com.kb.zipkim.domain.login.jwt.JWTUtil;
import com.kb.zipkim.domain.login.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BrokerController {

    private final BrokerService brokerService;
    private final JWTUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/api/broker")
    public BrokerDTO findBroker(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String brokerNumber = request.get("brokerNumber");

        return brokerService.getBroker(name, brokerNumber);
    }

    @GetMapping("/api/role")
    public ResponseEntity getRole(HttpServletRequest request) {
        String username = jwtUtil.getUsername(request.getHeader("Authorization").substring(7));
        String role = userService.getRole(username);
        return ResponseEntity.ok(role);
    }

}
