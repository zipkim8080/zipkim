package com.kb.zipkim.domain.bookMark.controller;


import com.kb.zipkim.domain.bookMark.dto.BookMarkRequest;
import com.kb.zipkim.domain.bookMark.entity.BookMark;
import com.kb.zipkim.domain.bookMark.repository.BookMarkRepository;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apis")
public class BookMakrCheck {

    @Autowired
    private BookMarkRepository bookMarkRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/checkDB", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<?> searchData(@RequestBody BookMarkRequest bookMarkRequest) {
        String username = bookMarkRequest.getUsername();
        String probid = bookMarkRequest.getProbid();
        UserEntity user = userRepository.findByUsername(username);
        BookMark bookMarks = bookMarkRepository.findByUserAndProbid(user, probid);
        if(bookMarks != null) {
            return ResponseEntity.ok(bookMarks);
        }
        return ResponseEntity.ok(null);
    }
}
