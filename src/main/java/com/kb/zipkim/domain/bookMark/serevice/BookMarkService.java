package com.kb.zipkim.domain.bookMark.serevice;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kb.zipkim.domain.bookMark.entity.BookMark;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.prop.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookMarkService {

//    private final BookMarkRepository bookMarkRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public String bookMarkProp(Long propId, String username) {
        Property prop = propertyRepository.findById(propId).orElseThrow(() -> new NotFoundException("해당 매물이 없습니다. id: " + propId));
        UserEntity user = userRepository.findByUsername(username);

//        bookMarkRepository.findByPropertyAndUserEntity(prop, user).ifPresent();
        return null;
    }
}
