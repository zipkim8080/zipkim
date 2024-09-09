package com.kb.zipkim;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class J0GitTest {
    @GetMapping("/j0")
    public String j0() {
        return "j0";
    }
}
