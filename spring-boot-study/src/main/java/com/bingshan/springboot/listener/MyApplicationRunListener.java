package com.bingshan.springboot.listener;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 自定义 SpringApplicationRunListener
 *      : 指定参数 SpringApplication  String[]的构造方法
 *      : 在程序spring.factories中进行注册配置
 */
public class MyApplicationRunListener implements SpringApplicationRunListener {

    public MyApplicationRunListener(SpringApplication springApplication, String[] args) {
        System.out.println("MyApplicationRunListener constructed function");
    }

    @Override
    public void starting(ConfigurableBootstrapContext bootstrapContext) {
        System.out.println("Starting...");
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        System.out.println("environmentPrepared...");
    }
}
