package com.kb.zipkim.domain.login.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneNumberRequest {
    private String phoneNumber;
    private String brokerNumber;

    @JsonCreator
    public PhoneNumberRequest(@JsonProperty("phoneNumber") String phoneNumber,
                              @JsonProperty("brokerNumber") String brokerNumber) {
        this.phoneNumber = phoneNumber;
        this.brokerNumber = brokerNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBrokerNumber() { return brokerNumber; }

}