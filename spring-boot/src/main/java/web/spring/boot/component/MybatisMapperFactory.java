package web.spring.boot.component;

import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import web.spring.boot.entity.GenericUser;

import javax.annotation.PostConstruct;

/**
 * Mybatis Mapper 动态生成和调度
 * <p>1、先注册 sqlStatement </p>
 * <p>2、通过调用 sqlStatement id 来使用 mapper 相关的功能</p>
 */
@Configuration
public class MybatisMapperFactory {
    private static final Logger logger = LoggerFactory.getLogger(MybatisMapperFactory.class);

    @Autowired
    SqlSessionFactory factory;


    @PostConstruct
    public void init() {
        logger.info("MybatisMapperFactory initialized");

    }

    @Bean
    public MybatisMapper userMapper2() {
        String namespace = "web.spring.boot.Mapper.UserMapper2";
        MybatisMapper mapper = new MybatisMapper(namespace, factory);

        // 可以读 resource 文件模板生成 MappedStatement
        mapper.addSqlStatement("selectUserById",
                "select * from dim_cfg_users where user_id = #{userId}",
                SqlCommandType.SELECT,
                Integer.class,
                GenericUser.class);

        mapper.addSqlStatement("selectUserByName",
                "select * from dim_cfg_users where username = #{username}",
                SqlCommandType.SELECT,
                String.class,
                GenericUser.class);

        mapper.addSqlStatement("insertUser",
                "INSERT into dim_cfg_users (" +
                        ")",
                SqlCommandType.INSERT,
                GenericUser.class,
                Integer.class);

        logger.info("Init bean userMapper2. id: selectUserById");



        return mapper;
    }

}
