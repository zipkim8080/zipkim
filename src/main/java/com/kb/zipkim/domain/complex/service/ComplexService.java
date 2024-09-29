package com.kb.zipkim.domain.complex.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.zipkim.domain.complex.dto.SearchResponse;
import com.kb.zipkim.domain.complex.entity.Complex;
import com.kb.zipkim.domain.complex.repository.ComplexRepository;
import com.kb.zipkim.domain.complex.dto.ComplexInfo;
import com.kb.zipkim.domain.complex.dto.NearByComplex;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplexService {
    private static final String KEY = "complexes";

    private final ComplexRepository complexRepository;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;
    public List<NearByComplex> findNearComplexes(String type, double latitude, double longitude, double radius) throws JsonProcessingException {
        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();
        GeoReference<String> reference = GeoReference.fromCoordinate(new Point(longitude, latitude));
        Distance distance = new Distance(radius, RedisGeoCommands.DistanceUnit.KILOMETERS);

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeCoordinates()
                .includeDistance()
                .sortAscending();

        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOperations.search(KEY, reference, distance, args);
        List<NearByComplex> complexes = new ArrayList<>();

        if(results == null) return complexes; //주변값이 없으면 빈 배열반환

        for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
            RedisGeoCommands.GeoLocation<String> location = result.getContent();
            NearByComplex complex = objectMapper.readValue(location.getName(), NearByComplex.class);
            if (!complex.getType().equals(type) ) {
                continue;
            }
            complex.setDistance(result.getDistance().getValue());
            complexes.add(complex);
        }
        return complexes;
    }

    // 평 정보 추가해야함
    public ComplexInfo findById(Long complexId) {
        Complex findComplex = complexRepository.findById(complexId)
                .orElseThrow(() -> new NotFoundException("해당 단지정보가 없습니다 단지Id: " + complexId));
        return new ComplexInfo(findComplex.getId(), findComplex.getBgdCd(), findComplex.getComplexName(), findComplex.getRoadName(), findComplex.getRecentAmount(), findComplex.getRecentDeposit(), findComplex.getAddressName(), findComplex.getMainAddressNo(), findComplex.getSubAddressNo());
    }

    public List<SearchResponse> findWithBgd(String bgdCd) {
        List<Complex> find = complexRepository.findByBgdCd(bgdCd);
        List<SearchResponse> responses = new ArrayList<>();
        for (Complex complex : find) {
            responses.add(new SearchResponse(complex.getId(), complex.getComplexName(), complex.getType(), complex.getAddressName(), complex.getLatitude(), complex.getLongitude()));
        }
        return responses;
    }

}
