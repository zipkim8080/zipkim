package com.kb.zipkim.domain.broker.service;

import com.kb.zipkim.domain.broker.dto.BrokerDTO;
import com.kb.zipkim.domain.broker.mapper.BrokerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BrokerServiceImpl implements BrokerService {

    private final BrokerMapper brokerMapper;

    @Override
    public BrokerDTO getBroker(String name, String brokerNumber) {
        return brokerMapper.getBroker(name, brokerNumber);
    }
}
