package web.spring.boot.mapper;

import web.spring.boot.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<User> selectUserById(int userId);

    public List<User> selectUserByName(String name);

    public int insertUser(User users);

}
