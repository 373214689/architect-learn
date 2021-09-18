package com.liuyang.web;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.liuyang.web.mapper")
public class StartSpringBoot {

    public static void main(String[] args) {

        SpringApplication.run(StartSpringBoot.class);
    }
}
