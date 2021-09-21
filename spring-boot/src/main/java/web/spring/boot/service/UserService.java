package web.spring.boot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.spring.boot.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public String getToken(HttpServletRequest request,
                           HttpServletResponse response)
    {
        return null;
    }

    public void removeToken(HttpServletRequest request,
                            HttpServletResponse response)
    {

    }

    public void saveToken(HttpServletRequest request,
                          HttpServletResponse response,
                          String token)
    {

    }





}
