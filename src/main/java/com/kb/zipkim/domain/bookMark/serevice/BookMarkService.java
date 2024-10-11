package com.kb.zipkim.domain.bookMark.serevice;

import com.kb.zipkim.domain.bookMark.entity.BookMark;
import com.kb.zipkim.domain.bookMark.repository.BookMarkRepository;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import com.kb.zipkim.domain.prop.dto.SimplePropInfo;
import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.prop.file.FileStoreService;
import com.kb.zipkim.domain.prop.file.UploadFile;
import com.kb.zipkim.domain.prop.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final FileStoreService fileStoreService;

    @Transactional
    public void addBookMark(String username, Long propertyId) {
        UserEntity user = userRepository.findByUsername(username);
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new IllegalArgumentException("Property not found"));
        bookMarkRepository.findByUserAndProperty(user, property).ifPresent(bookmark -> { throw new IllegalArgumentException("Bookmark already exists"); });
        BookMark bookMark = BookMark.builder()
                .user(user)
                .property(property)
                .build();
        bookMarkRepository.save(bookMark);
    }

    @Transactional
    public void deleteBookMark(String username, Long propertyId) {
        UserEntity user = userRepository.findByUsername(username);
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new IllegalArgumentException("Property not found"));
        BookMark bookMark = bookMarkRepository.findByUserAndProperty(user, property).orElseThrow(() -> new IllegalArgumentException("Bookmark does not exist"));
        bookMarkRepository.delete(bookMark);
    }

    public boolean hasBookMark(String username, Long propertyId) {
        UserEntity user = userRepository.findByUsername(username);
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new IllegalArgumentException("Property not found"));
        return bookMarkRepository.findByUserAndProperty(user, property).isPresent();
    }

    public Page<SimplePropInfo> getList(String username, Pageable pageable) {
        UserEntity user = userRepository.findByUsername(username);

        Page<BookMark> bookMarks = bookMarkRepository.findByUser(user,pageable);
        List<SimplePropInfo> list = new ArrayList<>();
        for (BookMark bookMark : bookMarks) {
            Property property = bookMark.getProperty();
            List<UploadFile> images = property.getImages();
            String imageUrl = !images.isEmpty()? fileStoreService.getFullPath(images.get(0).getStoreFileName()) : null;
            list.add(new SimplePropInfo(property,imageUrl,true ));
        }
        return new PageImpl<>(list,pageable,bookMarks.getTotalElements());
    }
}
