package com.kb.zipkim.domain.bookMark.controller;


import com.kb.zipkim.domain.bookMark.dto.BookMarkRequest;
import com.kb.zipkim.domain.bookMark.serevice.BookMarkService;
import com.kb.zipkim.domain.login.entity.UserEntity;
import com.kb.zipkim.domain.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis")
public class BookMarkAddController {

    @Autowired
    private BookMarkService bookMarkService;
    @Autowired
    private UserRepository userRepository;

    public BookMarkAddController(BookMarkService bookMarkService) {
        this.bookMarkService = bookMarkService;
    }
    @RequestMapping(value = "/addDB", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> addBookMark(@RequestBody(required = false) BookMarkRequest bookMarkRequest) {
        String username = bookMarkRequest.getUsername();
        String probid = bookMarkRequest.getProbid();
        String deposit = bookMarkRequest.getDeposit();
        String amount = bookMarkRequest.getAmount();
        String floor = bookMarkRequest.getFloor();
        String image = bookMarkRequest.getImage();

        UserEntity user = userRepository.findByUsername(username);

        bookMarkService.addbookmark(user, probid, deposit, amount, floor, image);
        return ResponseEntity.ok("저장 성공");
    }
}