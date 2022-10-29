package com.bingshan.springboot;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;

/**
 * 3.5.2 ApplicationContextInitializer加载, 实例讲解
 *  自定义一个 ApplicationContextInitializer
 *
 *  可以通过3种方式调用该类:
 *      1. 参考Spring Boot 源代码中的操作, 该实现类配置在 META-INF/spring.factories文件中.
 *      2. 通过 application.properties 或者 application.yml 文件进行配置, 格式如下:
 *          context.initializer.classes=xxx.LearnApplicationContextInitializer
 *      3. 通过SpringApplication提供的addInitializers方法进行追加配置.
 */
@Order(123)
public class LearnApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        //打印容器里面初始化了多少个Bean
        System.out.println("容器中初始化Bean数量: " + applicationContext.getBeanDefinitionCount());
    }
}
