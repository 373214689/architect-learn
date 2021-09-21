package web.spring.boot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/v1")
public class UserContorller {

    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {

        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {

        return "login";
    }

    @RequestMapping("/register")
    public String register(HttpServletRequest request, HttpServletResponse response) {

        return "register";
    }


}
