package com.kb.zipkim.domain.bookMark.serevice;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kb.zipkim.domain.bookMark.entity.BookMark;
import com.kb.zipkim.domain.bookMark.repository.BookMarkRepository;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.prop.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
/*@Transactional(readOnly = true)
@RequiredArgsConstructor*/
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    /*private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;*/
    public BookMarkService(BookMarkRepository bookMarkRepository) {
        this.bookMarkRepository = bookMarkRepository;
    }

    public List<BookMark> getdataByUsername(String username) {
        return bookMarkRepository.findByUsername(username);
    }

    public void addbookmark(String username, String phonenumber) {
        BookMark savedBookmark = bookMarkRepository.findByUsernameAndPhonenumber(username, phonenumber);
        if (savedBookmark != null) {
            bookMarkRepository.delete(savedBookmark);
            System.out.println("즐겨찾기 삭제");
        } else {
            BookMark bookmark = new BookMark();
            bookmark.setUsername(username);
            bookmark.setPhonenumber(phonenumber);
            bookMarkRepository.save(bookmark);
            System.out.println("즐겨찾기 저장");
        }
    }
}
