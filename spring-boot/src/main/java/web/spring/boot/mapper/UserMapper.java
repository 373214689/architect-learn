package web.spring.boot.mapper;

import web.spring.boot.entity.GenericUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<GenericUser> selectUserById(int userId);

    public List<GenericUser> selectUserByName(String name);

    public int insertUser(GenericUser users);

}
