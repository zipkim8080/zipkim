package com.kb.zipkim.domain.bookMark.serevice;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kb.zipkim.domain.bookMark.entity.BookMark;
import com.kb.zipkim.domain.bookMark.repository.BookMarkRepository;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import com.kb.zipkim.domain.prop.entity.Property;
import com.kb.zipkim.domain.prop.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
/*@Transactional(readOnly = true)
@RequiredArgsConstructor*/
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;
    private final UserRepository userRepository;

    public BookMarkService(BookMarkRepository bookMarkRepository, UserRepository userRepository) {
        this.bookMarkRepository = bookMarkRepository;
        this.userRepository = userRepository;
    }

    public void addbookmark(UserEntity user, String probid, String desopit, String amount, String floor, String image) {
        BookMark savedBookmark = bookMarkRepository.findByUserAndProbid(user, probid);
        if (savedBookmark != null) {
            bookMarkRepository.delete(savedBookmark);
            System.out.println("즐겨찾기 삭제" + probid);
        } else {
            BookMark bookmark = new BookMark();
            bookmark.setUser(user);
            bookmark.setProbid(probid);
            bookmark.setDeposit(desopit);
            bookmark.setAmount(amount);
            bookmark.setFloor(floor);
            bookmark.setImage(image);
            bookMarkRepository.save(bookmark);
            System.out.println("즐겨찾기 저장" + probid);
        }
    }

    public boolean deleteItem(Long itemId) {
        Optional<BookMark> item = bookMarkRepository.findById(itemId);
        if (item.isPresent()) {
            bookMarkRepository.delete(item.get());
            return true;
        }
        return false;
    }
    public boolean updateItem(Long itemId, String info) {
        Optional<BookMark> item = bookMarkRepository.findById(itemId);

        if(item.isPresent()) {
            BookMark existingItem = item.get();
            existingItem.setProbid(info);
            bookMarkRepository.save(existingItem);
            return true;
        } else {
            return false;
        }
    }
}
