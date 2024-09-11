package com.kb.zipkim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableFeignClients
public class ZipkimApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkimApplication.class, args);
    }

}
