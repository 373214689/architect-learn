package com.liuyang.web.mapper;

import com.liuyang.web.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserMapper {


    public List<User> selectUserById(int userId);


}
