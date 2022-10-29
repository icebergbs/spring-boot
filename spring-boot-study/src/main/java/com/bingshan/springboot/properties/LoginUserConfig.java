package com.bingshan.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Value 是对单个属性进行注入配置,如果有很多配置属性或者配置属性本身拥有层及结构,
 *
 * 通过@ConfigurationProperties注解将properties属性 和 LoginUserConfig的属性进行关联, 从而实现类型安全配置.
 */
@Component
@ConfigurationProperties(prefix = "user")
public class LoginUserConfig {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
