package web.spring.boot.mapper;

import web.spring.boot.entity.GeneralUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public List<GeneralUser> selectUserById(int userId);

    public List<GeneralUser> selectUserByName(String name);

    public int insertUser(GeneralUser users);

}
