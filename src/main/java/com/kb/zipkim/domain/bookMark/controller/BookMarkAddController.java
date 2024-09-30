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
public class BookMarkAddController {

    @Autowired
    private BookMarkService bookMarkService;

    public BookMarkAddController(BookMarkService bookMarkService) {
        this.bookMarkService = bookMarkService;
    }
    @RequestMapping(value = "/addDB", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> addBookMark(@RequestBody(required = false) BookMarkRequest bookMarkRequest) {
        String username = bookMarkRequest.getUsername();
        String phonenumber = bookMarkRequest.getPhonenumber();
        /*String 건물유형 = bookMarkRequest.get건물유형();
        String 매매가 = bookMarkRequest.get매매가();
        Double 위치 = bookMarkRequest.get위치();
        Long 평수 = bookMarkRequest.get평수();
        String 설명 = bookMarkRequest.get설명();*/

        bookMarkService.addbookmark(username, phonenumber);
        return ResponseEntity.ok("저장 성공");
    }
}
