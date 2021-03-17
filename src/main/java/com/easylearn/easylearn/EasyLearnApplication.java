package com.easylearn.easylearn;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@OpenAPIDefinition(
        info = @Info(
                title = "Easy Learn app",
                version = "0.1",
                description = "Application for YP",
                license = @License(name = "Trade secret"),
                contact = @Contact(url = "https://www.easy-learn.com/eng/")))
@SpringBootApplication
@EnableCaching
public class EasyLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyLearnApplication.class, args);
    }

}
