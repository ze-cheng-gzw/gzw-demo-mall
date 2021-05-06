package com.demo.service;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.demo.dao")
@SpringBootApplication
//@EnableScheduling
@EnableDubbo
public class DemoMallServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMallServiceApplication.class, args);
    }

}
