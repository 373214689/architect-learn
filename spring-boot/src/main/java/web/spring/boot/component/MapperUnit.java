package web.spring.boot.component;


import web.spring.boot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
@Component
public class MapperUnit {

    private static MapperUnit mapperUnit;

    @Resource
    private UserMapper userMapper;


    @PostConstruct
    public void init() {
        mapperUnit = this;
    }

    public static UserMapper userMapper() {
        return mapperUnit.userMapper;
    }



}
