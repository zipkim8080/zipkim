package com.kb.zipkim.domain.prop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.zipkim.domain.prop.dto.NearByProp;
import com.kb.zipkim.domain.prop.dto.PropRegisterForm;
import com.kb.zipkim.domain.prop.dto.RegisterResult;
import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.prop.file.FileStoreService;
import com.kb.zipkim.domain.prop.file.UploadFile;
import com.kb.zipkim.domain.prop.repository.PropertyRepository;
import com.kb.zipkim.domain.register.Registered;
import jakarta.transaction.Transactional;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private static final String KEY = "prop";
    private final PropertyRepository propertyRepository;
    private final FileStoreService fileStoreService;
    private final ObjectMapper objectMapper;

    private final RedisTemplate<String,String> redisTemplate;

    @Transactional
    public RegisterResult registerProp(PropRegisterForm form) throws IOException {
        List<UploadFile> uploadFiles = fileStoreService.storeFiles(form.getImages());

        Property property = Property.makeProperty(form);
        Registered registered = Registered.builder()
                .registerUniqueNum(form.getRegisterUniqueNum())
                .build();
        property.register(registered);
        property.upload(uploadFiles);
        propertyRepository.save(property);
        RegisterResult result = new RegisterResult();
        for (UploadFile uploadFile : uploadFiles) {
            result.getImageUrl().add(fileStoreService.getFullPath(uploadFile.getStoreFileName()));
        }
        result.setPropId(property.getId());
        return result;
    }

    public List<NearByProp> findNearProp(double latitude, double longitude, double radius) throws JsonProcessingException {
        GeoOperations<String, String> geoOperations = redisTemplate.opsForGeo();
        GeoReference<String> reference = GeoReference.fromCoordinate(new Point(longitude, latitude));
        Distance distance = new Distance(radius, RedisGeoCommands.DistanceUnit.KILOMETERS);

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeCoordinates()
                .includeDistance()
                .sortAscending();

        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOperations.search(KEY, reference, distance, args);
        List<NearByProp> props = new ArrayList<>();

        if(results == null) return props; //주변값이 없으면 빈 배열반환

        for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
            RedisGeoCommands.GeoLocation<String> location = result.getContent();
            NearByProp prop = objectMapper.readValue(location.getName(), NearByProp.class);
            prop.setDistance(result.getDistance().getValue());
            props.add(prop);
        }
        return props;
    }

}
