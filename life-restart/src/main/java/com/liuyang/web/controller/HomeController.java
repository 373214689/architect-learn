package com.liuyang.web.controller;

import com.liuyang.web.entity.User;
import com.liuyang.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HomeController {


    @Autowired
    UserMapper userMapper;

    @RequestMapping("")
    public String index(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("name", "life restart");
        return "index";
    }

    @ResponseBody
    @RequestMapping("test")
    public List<User> test(HttpServletRequest request, HttpServletResponse response) {

        return userMapper.selectUserById(1);
    }
}
