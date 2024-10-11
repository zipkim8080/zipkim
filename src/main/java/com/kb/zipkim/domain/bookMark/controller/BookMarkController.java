package com.kb.zipkim.domain.bookMark.controller;

import com.kb.zipkim.domain.bookMark.dto.BookMarkRequest;
import com.kb.zipkim.domain.bookMark.serevice.BookMarkService;
import com.kb.zipkim.domain.login.dto.CustomOAuth2User;
import com.kb.zipkim.domain.prop.dto.RegisterResult;
import com.kb.zipkim.domain.prop.dto.SimplePropInfo;
import com.kb.zipkim.global.exception.AuthException;
import com.kb.zipkim.global.exception.ExceptionCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
@Slf4j
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @PostMapping("/add")
    public void bookMarkAdd(@RequestBody BookMarkRequest request, @AuthenticationPrincipal CustomOAuth2User user) throws IOException {
        bookMarkService.addBookMark(user.getUsername(),request.getPropertyId());
    }

    @PostMapping("/delete")
    public void bookMarkDelete(@RequestBody BookMarkRequest request, @AuthenticationPrincipal CustomOAuth2User user) throws IOException {
        bookMarkService.deleteBookMark(user.getUsername(),request.getPropertyId());
    }

    @GetMapping("/{propId}")
    public boolean hasBookMark(@PathVariable Long propId, @AuthenticationPrincipal CustomOAuth2User user) {
        return bookMarkService.hasBookMark(user.getUsername(), propId);
    }

    @GetMapping("/list")
    public Page<SimplePropInfo> bookMarkList(@AuthenticationPrincipal CustomOAuth2User user, Pageable pageable) {
        return bookMarkService.getList(user.getUsername(),pageable);
    }

}
