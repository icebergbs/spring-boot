package com.bingshan.springboot.controller;

import com.bingshan.springboot.properties.LoginUserConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Controller 用来标识当前类是一个Servlet
 * @RestController 集成@Controller，它告诉Spring Boot这是一个基于RESTful风格的HTTP端点，并且会自动使用JSON实现HTTP请求和响应的序列化/反序列化操作
 */
@RestController
public class TestController {

    @Resource
    private LoginUserConfig loginUserConfig;

    @RequestMapping("/mock")
    public String mock(String name) {
        return "Hello " + name + "!";
    }


    @RequestMapping("/getConfigParams")
    public void getConfigParams() {

        //通过 @ConfigurationProperties注解配置的参数
        System.out.println("@COnfigurationProperties config Username : " + loginUserConfig.getUsername() );
        System.out.println("@COnfigurationProperties config Password : " + loginUserConfig.getPassword() );
    }
}
