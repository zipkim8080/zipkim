package com.kb.zipkim.domain.broker.service;

import com.kb.zipkim.domain.broker.dto.BrokerDTO;

public interface BrokerService {
    BrokerDTO getBroker(String name, String brokerNumber);
}
