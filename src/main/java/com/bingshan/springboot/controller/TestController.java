package com.bingshan.springboot.controller;

import com.bingshan.springboot.properties.LoginUserConfig;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
