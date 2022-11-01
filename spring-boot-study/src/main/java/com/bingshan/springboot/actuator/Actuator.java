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

     配置文件里面这样配置，则可以开启actuator所有的入口：
        management.endpoints.web.exposure.include=*

     根据端点的作用，可以把原生端点分为如下三类：
        应用配置类： 获取应用程序中加载的应用配置、环境变量、自动化配置报告等与Spring Boot应用密切相关的配置类信息。
        度量指标类： 获取应用程序运行过程中用于监控的度量指标，比如内存信息、线程池信息、HTTP请求统计等。
        操作控制类： 在原生端点中，只提供了一个用来关闭应用的端点， 即/shutdown端点。

        1	/autoconfig	GET	自动配置报告，记录哪些自动配置条件是否通过
        2	/configprops	GET	描述配置属性（包括默认值）如何注入的
        3	/beans	GET	描述上下文所有bean，以及它们之间的关系
        5	/env	GET	获取全部环境属性
        6	/env/{name}	GET	获取特点环境属性
        8	/info	GET	获取应用程序定制信息，这些信息有info打头的属性提供
        9	/mappings	GET	描述全部URL路径，及它们和控制器（包括Actuator端点）的映射关系


        4	/dump	GET	获取线程活动快照
        7	/health	GET	应用程序健康指标，由HealthIndicator的实现类提供
        10	/metrics	GET	报告各种应用程序度量信息，比如内存用量和http请求计算
        11	/metrics/{name}	GET	报告指定名称的应用程序度量值
        13	/trace	GET	提供基本的HTTP请求跟踪信息，时间戳，HTTP头等


        12	/shutdown	GET	关闭应用程序，要求endpoints.shutdown.enabled设值为true


        默认提供的端点信息不能满足要求，可以对其进行修改和扩展。
        实现方案：
            1. 扩展现有的监控端点
            2. 自定义新的监控端点
 */

    /**
     * 扩展 Info端点
     *
     * 1. 通过配置文件中添加格式以 ”info"开头的配置段，
     *    可以定义Info端点暴露的数据
     *
     * 2. 自定义的 InfoContributor对象， 直接实现 InfoContributor接口的contribute()
     */

    /**
     * 扩展 Health 端点
     *
     * HealthIndicator对象.
     */

}
