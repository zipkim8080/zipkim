package com.kb.zipkim.domain.broker.mapper;

import com.kb.zipkim.domain.broker.dto.BrokerDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BrokerMapper {
    public BrokerDTO getBroker(@Param("name") String name, @Param("brokerNumber") String brokerNumber);
}
