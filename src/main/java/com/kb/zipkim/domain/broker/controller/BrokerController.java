package com.kb.zipkim.domain.broker.controller;

import com.kb.zipkim.domain.broker.dto.BrokerDTO;
import com.kb.zipkim.domain.broker.service.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BrokerController {

    private final BrokerService brokerService;

    @PostMapping("/api/broker")
    public BrokerDTO findBroker(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String brokerNumber = request.get("brokerNumber");

        return brokerService.getBroker(name, brokerNumber);
    }
}
