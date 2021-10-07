package com.liuyang.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableAutoConfiguration
@EnableConfigServer
@EnableEurekaClient
public class StartConfigServer {

    public static void main(String[] args) {

        SpringApplication.run(StartConfigServer.class);

    }
}
