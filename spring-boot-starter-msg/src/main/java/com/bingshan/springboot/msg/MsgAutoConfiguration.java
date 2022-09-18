package com.bingshan.springboot.msg;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


/**
 * 创建Spring boot 自动配置项目
 *
 *  自动配置starter的基本条件：
 *      首先，需要在classpath中存在用于判断是否进行自动配置的类；
 *      然后，当满足这些条件之后，需要通过自定义的Bean将其实例化并注册到容器中；
 *      最后，这一过程通过SpringBoot自动配置的机制自动完成。
 *
 *  自定义spring boot starter 项目：
 *      1. 创建一个简单的maven项目
 *      2. pom.xml 引入Spring boot 自动化配置依赖 spring-boot-autoconfigure
 *          <dependency>
 *             <groupId>org.springframework.boot</groupId>
 *             <artifactId>spring-boot-autoconfigure</artifactId>
 *             <version>2.5.13</version>
 *         </dependency>
 *
 *      3. 在当前项目resource目录下创建 META-INF/spring.factories文件，并对自动配置类进行注册；如果多个自动配置类，用逗号分割
 *          org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
 *          com.bingshan.springboot.msg.MsgAutoConfiguration
 *      4. 使用maven将其打包到本地仓库或上传至私服， 其他项目可以通过maven依赖进行使用
 *
 *
 */


/**
 * 自动配置类：
 *      整合属性配置类和服务类， 并在特定的条件下进行实例化
 *
 * @Configuration 用来声明该类为一个配置类
 * @ConditionalOnClass 当MsgService 类存在于classpath中， 该类才会进行相应的实例化
 * @EnableConfigurationProperties 会将application.properties中对应的属性配置设置于MsgProperties对象中
 */
@Configuration
@ConditionalOnClass(MsgService.class)
@EnableConfigurationProperties({MsgProperties.class})
public class MsgAutoConfiguration {

    /**
     * 注入属性配置类
     */
    @Resource
    private MsgProperties msgProperties;

    /**
     * 在引用starter的项目 properties中配置
     *      msg.enabled=true
     *      找不到 MsgService bean , MsgService  bean没有被创建
     *      改成下面可以
     *
     * @Bean 该方法实例化的对象会被加载到容器中；
     * @ConditionalOnMissiongBean 表示当容器中不存在MsgService的对象时，才会进行实例化操作；
     * @ConditionalOnProperty表示当配置文件中 msg.enabled=true时，才会进行实例化操作
     * @return
     */
    //@ConditionalOnProperty(prefix = "msg", value = "enabled", havingValue = "true")
    @Bean
    @ConditionalOnMissingBean(MsgService.class)
    @ConditionalOnProperty(prefix = "msg", value = "enabled", matchIfMissing = true)
    public MsgService msgService() {
        MsgService msgService = new MsgService(msgProperties.getUrl(), msgProperties.getAccessKeyId(),
                msgProperties.getAccessKeySecret());
        //如果提供了其他set方法,在此也可以调用对应方法对其进行相应的设置或初始化
        return msgService;

    }
}
