package com.vb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationTest {

    public static final Long existEntityId = 1L;
    public static final Long notExistEntityId = 2L;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class);
    }

}
