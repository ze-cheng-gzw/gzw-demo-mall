package com.demo.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoMallOrderWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMallOrderWebApplication.class, args);
    }

}
