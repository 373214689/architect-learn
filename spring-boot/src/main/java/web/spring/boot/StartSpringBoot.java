package web.spring.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * 需要注意的是启动类和入口类都需要注解 @SpringBootApplication
 * spring web.spring.spring.web.spring.boot 启动入口
 * 注解后，spring web.spring.spring.web.spring.boot 会扫描并加载 Bean 到容器，然后进行启动
 */
@SpringBootApplication
@MapperScan("web.spring.boot.mapper")
@EnableAutoConfiguration
public class StartSpringBoot {
    public static void main(String[] args) {
        SpringApplication.run(StartSpringBoot.class, args);
    }
}
