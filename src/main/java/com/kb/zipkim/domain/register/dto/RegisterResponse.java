package com.kb.zipkim.domain.register.dto;

import com.kb.zipkim.domain.register.entity.Registered;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {

    private String uniqueNumber;

    private String openDate;

    private String address;

    private Boolean attachment1;

    private Boolean attachment2;

    private Boolean trust;

    private Boolean auction;

    private double loan;

    private double leaseAmount;

    public static RegisterResponse toResponse(Registered register) {
        return RegisterResponse.builder()
                .uniqueNumber(register.getUniqueNumber())
                .openDate(register.getOpenDate())
                .address(register.getAddress())
                .attachment1(register.getAttachment1())
                .attachment2(register.getAttachment2())
                .trust(register.getTrust())
                .auction(register.getAuction())
                .loan(register.getLoan())
                .leaseAmount(register.getLeaseAmount())
                .build();
    }
}
