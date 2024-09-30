package com.kb.zipkim.domain.prop.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import com.kb.zipkim.domain.prop.dto.*;
import com.kb.zipkim.domain.complex.entity.Complex;
import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.prop.file.FileStoreService;
import com.kb.zipkim.domain.prop.file.UploadFile;
import com.kb.zipkim.domain.prop.repository.ComplexPropQueryRepository;
import com.kb.zipkim.domain.complex.repository.ComplexRepository;
import com.kb.zipkim.domain.prop.repository.PropertyRepository;
import com.kb.zipkim.domain.register.entity.Registered;
import com.kb.zipkim.domain.register.repository.RegisteredRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final ComplexRepository complexRepository;
    private final FileStoreService fileStoreService;
    private final ComplexPropQueryRepository complexPropQueryRepository;
    private final RegisteredRepository registeredRepository;
    private final UserRepository userRepository;
    @Transactional
    public RegisterResult registerProp(PropRegisterForm form,String username) throws IOException {
        List<UploadFile> uploadFiles = fileStoreService.storeFiles(form.getImages());
        UserEntity broker = userRepository.findByUsername(username);

        Property property = Property.makeProperty(form,broker);

        if(registeredRepository.existsByUniqueNumber(form.getUniqueNumber())){
            throw new IllegalArgumentException("이미 존재하는 등기입니다. 등기번호: "+ form.getUniqueNumber());
        };
        Registered registered = registeredRepository.save(new Registered(form.getUniqueNumber(), form.getOpenDate(), form.getAddress(), form.getAttachment1(), form.getAttachment2(), form.getTrust(), form.getAuction(), form.getLoan(), form.getLeaseAmount()));
        property.register(registered);
        property.upload(uploadFiles);
        Complex complex = getComplex(form);

//                    NearByComplex nearByComplex = new NearByComplex(newComplex.getId(),newComplex.getType(), newComplex.calcAvrDeposit(), newComplex.calcAvrAmount(), newComplex.getRecentAmount(), newComplex.getRecentDeposit(), newComplex.calcCurrentDepositRatio(), newComplex.calcRecentDepositRatio(), newComplex.getLongitude(),newComplex.getLatitude());
//                    Point point = new Point(newComplex.getLongitude(), newComplex.getLatitude());
//                    geoOperations.add(KEY, point, objectMapper.writeValueAsString(nearByComplex));
//        complex.updateRecentAmountAndDeposit(form.getRecentAmount(), form.getRecentDeposit());
        property.belongTo(complex);
        propertyRepository.save(property);
        RegisterResult result = new RegisterResult();
        for (UploadFile uploadFile : uploadFiles) {
            result.getImageUrl().add(fileStoreService.getFullPath(uploadFile.getStoreFileName()));
        }
        result.setPropId(property.getId());
        return result;
    }

    private Complex getComplex(PropRegisterForm form) {
        Complex complex;
        if(form.getType().equals("apt") || form.getType().equals("opi")) {
            complex = complexRepository.findById(form.getComplexId()).orElseThrow(() -> new NotFoundException("해당 단지정보가 없습니다 단지Id: " + form.getComplexId()));
        }else{
            complex = complexRepository.findByBgdCdAndMainAddressNoAndSubAddressNo(form.getBgdCd(), form.getMainAddressNo(), form.getSubAddressNo())
                    .orElseGet(()->{
                        Complex newComplex = Complex.makeComplex(form);
                        return complexRepository.save(newComplex);
                    });
        }
        return complex;
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

    public DetailPropInfo findPropById(Long id) {
        Property property = complexPropQueryRepository.findByIdWithRegisterAndImages(id);
        DetailPropInfo detailInfo = DetailPropInfo.toDetailPropInfo(property);
        List<UploadFile> images = property.getImages();

        List<PropImage> imageUrl = images.stream()
                .map(image -> new PropImage(image.getId(), fileStoreService.getFullPath(image.getStoreFileName())))
                .collect(Collectors.toList());

        detailInfo.setImages(imageUrl);
        return detailInfo;
    }

}
