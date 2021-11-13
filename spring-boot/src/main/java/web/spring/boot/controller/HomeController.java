package web.spring.boot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import web.spring.boot.component.MybatisMapper;
import web.spring.boot.entity.Message1;
import web.spring.boot.entity.User;
import web.spring.boot.mapper.UserMapper;
import web.spring.boot.service.UserService;
import web.spring.boot.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/hello")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("SpringBoot")
    public String SpringBoot() {
        return "Hello World! This is my SpringBoot";
    }


    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    MybatisMapper userMapper2;

    @RequestMapping("")
    public String home(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {

        Message1<String> message1 = userService.verify(request, response);

        logger.info("/hello. {}", JsonUtil.writeValueAsString(message1));

        if (message1.getCode() >= 400) {
            response.sendRedirect("/v1/login");
        }


        request.setAttribute("name", "life restart");
        return "index";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {

        return "index";
    }

    @ResponseBody
    @RequestMapping("test")
    public List<User> test(HttpServletRequest request, HttpServletResponse response) {
        return userMapper2.selectList("selectUserById", 1);
        //return userMapper.selectUserById(1);
    }
}
