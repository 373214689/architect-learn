package com.liuyang.test;


import com.liuyang.web.StartSpringBoot;
import com.liuyang.web.component.MapperUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartSpringBoot.class)
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserMapper {

    @Test
    public void selectUser() {

        MapperUnit.userMapper().selectUserById(1);
    }
}
