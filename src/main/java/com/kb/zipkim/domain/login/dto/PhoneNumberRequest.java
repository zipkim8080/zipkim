package com.kb.zipkim.domain.login.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneNumberRequest {
    private String phoneNumber;
//    private String username;

    @JsonCreator
    public PhoneNumberRequest(@JsonProperty("phoneNumber") String phoneNumber) {
        this.phoneNumber = phoneNumber;
//        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

//    public String getUsername() {
//        return username;
//    }
}