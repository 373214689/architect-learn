package web.spring.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import web.spring.boot.demo.HelloWorld;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring web.spring.spring.web.spring.boot 启动入口
 */
@SpringBootApplication
public class StartSpringBoot {
    public static void main(String[] args) {
        SpringApplication.run(HelloWorld.class, args);
    }
}
