package com.kb.zipkim.domain.prop.dto;

import lombok.Data;

@Data
public class LocationWithRadius {
    private Double lat;
    private Double lon;
    private Double radius;

}
