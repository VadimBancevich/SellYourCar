package com.vb;

import com.vb.api.dao.RandomEntityFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = RandomEntityFactory.class)
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class);
    }

}
