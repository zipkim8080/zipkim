package com.kb.zipkim;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class MyPage {
    @GetMapping("/hihi")
    public String hitest() { return "잘 나와??";}
}
