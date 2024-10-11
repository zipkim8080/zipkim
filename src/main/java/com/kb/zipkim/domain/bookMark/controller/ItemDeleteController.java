//package com.kb.zipkim.domain.bookMark.controller;
//
//
//import com.kb.zipkim.domain.bookMark.dto.BookMarkRequest;
//import com.kb.zipkim.domain.bookMark.serevice.BookMarkService;
//import com.kb.zipkim.domain.login.entity.UserEntity;
//import com.kb.zipkim.domain.login.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//public class ItemDeleteController {
//    @Autowired
//    private BookMarkService bookMarkService;
//    @Autowired
//    private UserRepository userRepository;
//
//    public ItemDeleteController(BookMarkService bookMarkService) {
//        this.bookMarkService = bookMarkService;
//    }
//
//    @RequestMapping(value = "/items/{itemId}",method = RequestMethod.DELETE)
//    public ResponseEntity<String> deleteItem(@PathVariable Long itemId, @RequestBody(required = false)BookMarkRequest bookMarkRequest) {
//
//        boolean isDeleted = bookMarkService.deleteItem(itemId);
//        if(isDeleted){
//            return ResponseEntity.ok("Deleted Item");
//        }
//        else {
//            return ResponseEntity.ok("실패");
//        }
//    }
//}
