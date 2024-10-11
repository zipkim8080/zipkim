package com.kb.zipkim.domain.bookMark.controller;

import com.kb.zipkim.domain.bookMark.dto.BookMarkRequest;
import com.kb.zipkim.domain.bookMark.entity.BookMark;
import com.kb.zipkim.domain.bookMark.repository.BookMarkRepository;
import com.kb.zipkim.domain.bookMark.serevice.BookMarkService;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apis")
public class BookMarkController {

    @Autowired
    private BookMarkRepository bookMarkRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/searchDB", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<?> searchData(@RequestBody BookMarkRequest bookMarkRequest) {
        String username = bookMarkRequest.getUsername();
        UserEntity user = userRepository.findByUsername(username);
        List<BookMark> bookMarks = bookMarkRepository.findByUser(user);
        if(!bookMarks.isEmpty()) {
            return ResponseEntity.ok(bookMarks);
        }
        return ResponseEntity.notFound().build();
    }
}