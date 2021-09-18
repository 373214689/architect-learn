package web.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import web.spring.boot.entity.User;
import web.spring.boot.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/hello")
public class HomeController {
    @RequestMapping("SpringBoot")
    public String SpringBoot() {
        return "Hello World! This is my SpringBoot";
    }


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
