package web.spring.boot.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动类
 * 需要注意的是启动类和入口类都需要注解 @SpringBootApplication
 * 注解后，spring web.spring.spring.web.spring.boot 会扫描并加载 Bean 到容器，然后进行启动
 */
@SpringBootApplication
@RestController
@RequestMapping("/hello")
public class HelloWorld {
    @RequestMapping("SpringBoot")
    public String SpringBoot() {
        return "Hello World! This is my SpringBoot";
    }
}
