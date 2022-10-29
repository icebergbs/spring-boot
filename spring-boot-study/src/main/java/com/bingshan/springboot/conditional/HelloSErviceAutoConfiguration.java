package com.bingshan.springboot.conditional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.bingshan.springboot")
@ConditionalOnProperty(prefix = "study", name = "enabled", havingValue = "true")
public class HelloSErviceAutoConfiguration {
    HelloSErviceAutoConfiguration() {
        System.out.println("test @Conditional");
    }
}
