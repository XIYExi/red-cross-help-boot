package com.red.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.red.**"})
@SpringBootApplication
public class RedCrossApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedCrossApplication.class, args);
    }

}
