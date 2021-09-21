package web.spring.boot.util;


import org.springframework.beans.factory.annotation.Autowired;
import web.spring.boot.mapper.UserMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MapperUnit {

    private static MapperUnit mapperUnit;

    @Autowired
    private UserMapper userMapper;


    @PostConstruct
    public void init() {
        mapperUnit = this;
    }

    public static UserMapper userMapper() {
        return mapperUnit.userMapper;
    }



}
