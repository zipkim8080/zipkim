package com.kb.zipkim.domain.bookMark.controller;

import com.kb.zipkim.domain.bookMark.dto.BookMarkRequest;
import com.kb.zipkim.domain.bookMark.entity.BookMark;
import com.kb.zipkim.domain.bookMark.serevice.BookMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookMarkController {

    @Autowired
    private BookMarkService bookMarkService;

    @RequestMapping(value = "/searchDB", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<?> serarchData(@RequestBody BookMarkRequest bookMarkRequest) {
        String username = bookMarkRequest.getUsername();
        List<BookMark> bookmarks = bookMarkService.getdataByUsername(username);
        return ResponseEntity.ok(bookmarks);
    }
}
