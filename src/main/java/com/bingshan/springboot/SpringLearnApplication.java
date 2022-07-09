package com.bingshan.springboot;

import com.bingshan.springboot.properties.ArgsBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashSet;
import java.util.Set;

/**
 * 3.3 SpringApplication构造方法参数
 *  将入口类main方法中将 primarySources参数的值由 SpringLearmApplication 替换为 OtherApplication.class
 *  并将SpringLearnApplication类上的注解去掉
 *  执行 main方法 程序依然可以完成自动配置,可以正常访问.
 *  因此决定Spring Boot启动的入口类并不一定是 main方法所在的类, 而是直接或者间接被 @EnableAutoConfiguration标注的类.
 *
 */
public class SpringLearnApplication {

    public static void main(String[] args) {
        //SpringApplication.run(OtherApplication.class, args);

        /**
         * 3. 通过SpringApplication提供的addInitializers方法进行追加配置.
         */
        SpringApplication app = new SpringApplication(OtherApplication.class);
        //添加自定义ContextInitializer, 注意会覆盖掉默认配置的
        app.addInitializers(new LearnApplicationContextInitializer());
        //app.run(args);

        /**
         * 3.8.2 配置源配置
         *  除了直接通过Setter方法进行参数的配置, 还可以通过设置配置源参数对整个配置文件或配置类进行配置.
         *
         *  可以通过两个途径进行配置:
         *      SpringApplication构造方法参数  - primarrySources参数来配置普通类, 弊端无法指定XML配置和package的配置
         *      SpringApplication提供的 setSources(Set<String> sources)方法进行设置, 方法参数为String集合,
         *          可以传递类名\package名称\XML配置资源
         *
         *  无论通过构造参数的形式还是通过Setter方法, 在Spring Boot 中都会将其合并. SpringApplication提供了一个getAllSources(),
         *  能够将两者参数进行合并.
         */
        Set<String> set = new HashSet<>();
        set.add(WithoutAnnoConfiguration.class.getName());
        app.setSources(set);
        ConfigurableApplicationContext context = app.run(args);
        WithoutAnnoConfiguration bean = context.getBean(WithoutAnnoConfiguration.class);
        System.out.println(bean.getName());

        /**
         * 5.2.2  SpringApplication.run(args)传入的参数
         */
        ArgsBean bean1 = context.getBean(ArgsBean.class);
        bean1.printArgs();
    }


}
