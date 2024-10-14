package com.kb.zipkim.domain.prop.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kb.zipkim.domain.bookMark.repository.BookMarkRepository;
import com.kb.zipkim.domain.login.dto.CustomOAuth2User;
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
    private final BookMarkRepository bookMarkRepository;

    @Transactional
    public RegisterResult registerProp(PropRegisterForm form,String username) throws IOException {
        List<UploadFile> uploadFiles = fileStoreService.storeFiles(form.getImages());
        UserEntity broker = userRepository.findByUsername(username);

        Property property = Property.makeProperty(form,broker);

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


    private void validateForm(PropRegisterForm form) {
        if (form.getType().equals("apt") || form.getType().equals("opi")) {
            if(form.getComplexId() == null) throw new IllegalArgumentException("complexId는 필수입니다.");
        }else{
            if(form.getBgdCd()==null || form.getMainAddressNo() == null || form.getSubAddressNo() == null)
                throw new IllegalArgumentException("법정동코드, 지번은 필수입닌다.");
        }
    }

    private Complex getComplex(PropRegisterForm form) {
        validateForm(form);
        Complex complex;
        if(form.getType().equals("apt") || form.getType().equals("opi")) {
            complex = complexRepository.findById(form.getComplexId()).orElseThrow(() -> new NotFoundException("해당 단지정보가 없습니다 단지Id: " + form.getComplexId()));
        }else{
            complex = complexRepository.findByBgdCdAndMainAddressNoAndSubAddressNo(form.getBgdCd(), form.getMainAddressNo(), form.getSubAddressNo())
                    .orElseGet(()->{
                        Complex newComplex = Complex.makeDdOrYr(form);
                        return complexRepository.save(newComplex);
                    });
        }
        return complex;
    }

    public Page<SimplePropInfo> findPropList(Long complexId, CustomOAuth2User user, Pageable pageable) {
        Page<Property> findProps = propertyRepository.findByComplexId(complexId, pageable);
        List<SimplePropInfo> simplePropInfos = new ArrayList<>();
        for (Property findProp : findProps) {
            List<UploadFile> images = findProp.getImages();
            String imageUrl = !images.isEmpty()? fileStoreService.getFullPath(images.get(0).getStoreFileName()) : null;
            boolean isFavorite = false;
            if (user != null) {
                UserEntity userEntity = userRepository.findByUsername(user.getUsername());
                isFavorite = bookMarkRepository.findByUserAndProperty(userEntity, findProp).isPresent();
            }
            simplePropInfos.add(new SimplePropInfo(findProp,imageUrl,isFavorite));
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

    public Page<SimplePropInfo> getPropertiesByBrokerId(String username, Pageable pageable) {
//        UserEntity user = userRepository.findByUsername(username);
//
//        List<Property> properties = propertyRepository.findByBrokerId(user.getId());
//
//        List<SimplePropInfo> list = new ArrayList<>();
//        for (Property property : properties) {
//            List<UploadFile> images = property.getImages();
//            String imageUrl = !images.isEmpty() ? fileStoreService.getFullPath(images.get(0).getStoreFileName()) : null;
//            list.add(new SimplePropInfo(property, imageUrl, false));
//        }
//
//        return new PageImpl<>(list, pageable, properties.size());
        UserEntity user = userRepository.findByUsername(username);

        // Pageable로 데이터 조회
        Page<Property> properties = propertyRepository.findByBrokerId(user.getId(), pageable);

        // SimplePropInfo 리스트로 변환
        List<SimplePropInfo> list = properties.stream()
                .map(property -> {
                    List<UploadFile> images = property.getImages();
                    String imageUrl = !images.isEmpty() ? fileStoreService.getFullPath(images.get(0).getStoreFileName()) : null;
                    return new SimplePropInfo(property, imageUrl, false);
                })
                .collect(Collectors.toList());

        // Page 객체로 반환
        return new PageImpl<>(list, pageable, properties.getTotalElements());
    }

    public void deleteProp(Long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 매물이 없습니다."));
        bookMarkRepository.deleteByProperty(property);

        propertyRepository.deleteById(id);
    }

}
