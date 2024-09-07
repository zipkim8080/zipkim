package com.kb.zipkim;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class TestController {

    @GetMapping("/")
    public String test() {
        return "집킴이 API SERVER입니다.";
    }
}
