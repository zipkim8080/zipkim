package com.kb.zipkim;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class chanil {

    @GetMapping("/chanil")
        public String chanil() {
            log.info("chanil");
            return "chanil";
        }
}
