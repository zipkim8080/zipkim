package com.kb.zipkim.domain.prop.service;

import com.kb.zipkim.domain.prop.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;

}
