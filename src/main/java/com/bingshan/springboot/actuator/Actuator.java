package com.bingshan.springboot.actuator;

/**
 * @author bingshan
 * @date 2022/10/29 16:14
 */
public class Actuator {

    /**
     * 使用Actuator组件强化服务
     */

    /*
    Spring Boot 自带的系统监控功能，系统监控在Spring中是缺失的。
Actuator组件是Spring Boot提供的一种集成组件，可以实现应用系统的运行时状态管理、配置查看以及相关功能统计。

    引入Actuator组件， 依赖Spring HATEOAS组件
         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.hateoas</groupId>
            <artifactId>spring-hateoas</artifactId>
        </dependency>

     当启动Spring Boot应用程序时， 在启动日志里会发现自动添加了autoconfig、dump、beans、actuator、health等众多HTTP端点
     当访问 http://localhost:8080/application 端点时会得到如下结果，这种响应结果就是 HATEOAS风格的HTTP响应，
 */

}
