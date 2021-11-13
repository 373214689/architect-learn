package web.spring.boot.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import web.spring.boot.entity.Message1;
import web.spring.boot.service.UserService;
import web.spring.boot.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/v1")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(Model model, HttpServletResponse response) {

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("message", "");
        model.addAllAttributes(hashMap);
        return "login";
    }

    @RequestMapping("/verify")
    public String verify(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //String reserved = request.getParameter("reserved");
        Message1<String> message1 = userService.login(request,
                response,
                username,
                password);
        logger.info("/v1/verify. {}", JsonUtil.writeValueAsString(message1));
        if (message1.getCode() >= 400) {
            request.setAttribute("message", "用户登陆失败!");
            return "login";
        } else {
            return "index";
        }
    }

    @RequestMapping("/register")
    public String register(HttpServletRequest request, HttpServletResponse response) {

        return "register";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        return "logout";
    }



}
