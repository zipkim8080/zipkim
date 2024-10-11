//package com.kb.zipkim.domain.bookMark.controller;
//
//
//import com.kb.zipkim.domain.bookMark.dto.BookMarkRequest;
//import com.kb.zipkim.domain.bookMark.dto.ItemUpdateRequest;
//import com.kb.zipkim.domain.bookMark.entity.BookMark;
//import com.kb.zipkim.domain.bookMark.serevice.BookMarkService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//public class ItemModifyController {
//    @Autowired
//    private BookMarkService bookMarkService;
//
//    @RequestMapping(value =  "/items/modify/{itemId}", method = RequestMethod.PUT)
//    public ResponseEntity<String> updateItem(@PathVariable Long itemId, @RequestBody ItemUpdateRequest itemUpdateRequest) {
//        boolean isUpdated = bookMarkService.updateItem(itemId, itemUpdateRequest.getInfo());
//        if(isUpdated) {
//            return ResponseEntity.ok("Item updated successfully.");
//        } else {
//            return ResponseEntity.ok("Item could not be updated.");
//        }
//    }
//}
