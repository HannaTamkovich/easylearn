package com.easylearn.easylearn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EasyLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyLearnApplication.class, args);
    }

}
