package com.liuyang.spring.cloud.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/cfg")
public class ConfigController {

    @Value("${foo}")
    String foo;

    @Value("${bar}")
    String bar;

    @RequestMapping("/foo")
    public String foo() {
        return foo + "--" + bar;
    }
}
