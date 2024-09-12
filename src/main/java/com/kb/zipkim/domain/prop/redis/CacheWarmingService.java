package com.kb.zipkim.domain.prop.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.zipkim.domain.prop.entity.Complex;
import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.prop.repository.ComplexRepository;
import com.kb.zipkim.domain.prop.repository.PropertyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Profile("prod")
public class CacheWarmingService {

    private static final String KEY = "props";
    private final ComplexRepository complexRepository;
    private final PropertyRepository propertyRepository;
    private final ObjectMapper objectMapper;

    private final RedisTemplate<String, String> redisTemplate;

//    @PostConstruct
//    public void loadComplexData() {
//        List<Complex> all = complexRepository.findAll();
//        redisTemplate.delete(KEY);
//        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();
//
//    }

}
