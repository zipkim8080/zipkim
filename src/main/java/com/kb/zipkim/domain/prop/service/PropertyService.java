package com.kb.zipkim.domain.prop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.zipkim.domain.prop.dto.*;
import com.kb.zipkim.domain.prop.entity.Complex;
import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.prop.file.FileStoreService;
import com.kb.zipkim.domain.prop.file.UploadFile;
import com.kb.zipkim.domain.prop.repository.ComplexPropQueryRepository;
import com.kb.zipkim.domain.prop.repository.ComplexRepository;
import com.kb.zipkim.domain.prop.repository.PropertyRepository;
import com.kb.zipkim.domain.register.Registered;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private static final String KEY = "complexes";
    private final PropertyRepository propertyRepository;
    private final ComplexRepository complexRepository;
    private final FileStoreService fileStoreService;
    private final ObjectMapper objectMapper;

    private final RedisTemplate<String,String> redisTemplate;
    private final ComplexPropQueryRepository complexPropQueryRepository;

    @Transactional
    public RegisterResult registerProp(PropRegisterForm form) throws IOException {
        List<UploadFile> uploadFiles = fileStoreService.storeFiles(form.getImages());
        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();

        Property property = Property.makeProperty(form);
        Registered registered = Registered.builder()
                .registerUniqueNum(form.getRegisterUniqueNum())
                .build();
        property.register(registered);
        property.upload(uploadFiles);

        Complex complex = complexRepository.findByBgdCdAndMainAddressNoAndSubAddressNo(form.getBgdCd(), form.getMainAddressNo(), form.getSubAddressNo())
                .orElseGet(()->{
                    Complex newComplex = Complex.makeComplex(form);
//                    NearByComplex nearByComplex = new NearByComplex(newComplex.getId(),newComplex.getType(), newComplex.calcAvrDeposit(), newComplex.calcAvrAmount(), newComplex.getRecentAmount(), newComplex.getRecentDeposit(), newComplex.calcCurrentDepositRatio(), newComplex.calcRecentDepositRatio(), newComplex.getLongitude(),newComplex.getLatitude());
//                    Point point = new Point(newComplex.getLongitude(), newComplex.getLatitude());
//                    geoOperations.add(KEY, point, objectMapper.writeValueAsString(nearByComplex));
                    return complexRepository.save(newComplex);
                });
        complex.updateRecentAmountAndDeposit(form.getRecentAmount(), form.getRecentDeposit());
        property.belongTo(complex);
        propertyRepository.save(property);
        RegisterResult result = new RegisterResult();
        for (UploadFile uploadFile : uploadFiles) {
            result.getImageUrl().add(fileStoreService.getFullPath(uploadFile.getStoreFileName()));
        }
        result.setPropId(property.getId());
        return result;
    }

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

    public Page<SimplePropInfo> findPropList(Long complexId, Pageable pageable) {
        Page<Property> findProps = propertyRepository.findByComplexId(complexId, pageable);
        List<SimplePropInfo> simplePropInfos = new ArrayList<>();
        for (Property findProp : findProps) {
            List<UploadFile> images = findProp.getImages();
            String imageUrl = !images.isEmpty()? fileStoreService.getFullPath(images.get(0).getStoreFileName()) : null;
            simplePropInfos.add(new SimplePropInfo(findProp,imageUrl));
        }
        return new PageImpl<>(simplePropInfos, pageable, findProps.getTotalElements());
    }

    public Property findPropById(Long id) {
        Property property = complexPropQueryRepository.findByIdWithRegisterAndImages(id);
        //로그인시 추가 개선필요

        return property;
    }

}
