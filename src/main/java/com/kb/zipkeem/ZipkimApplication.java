package com.kb.zipkeem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ZipkimApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkimApplication.class, args);
    }

}
