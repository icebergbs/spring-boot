package com.bingshan.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Springboot:
     *  框架使用了特定的方式来进行配置.
     *  约定大于配置的原则.
     *
     *
     *  特点:
     *      创建独立的Spring应用程序
     *      嵌入的Tomcat,无须部署WAR文件
     *      简化Maven配置
     *      自动配置Spring
     *      提供生产就绪型功能,如指标 健康检查和外部配置
     *      绝对没有代码生成, 以及对XML没有配置要求
     *
     * SpringApplication 启动Spring:
     *  context = createApplicationContext();
     *  refreshContex(contex);
     *  afterRefresh(context, applicationArguments);
     *  spring初始化的方案最为核心的就是 创建\初始化\刷新
     *
     * bean的加载:
     *  prepareContext() {
     *      load() {
     *          BeanDefinitionLoader loader
     *      }
     *  }
     *
     *
     * Starter自动化配置原理:
     *   @EnableAutoConfiguration
     *      @Import(AutoConfigurationImportSelector.class)
     *          isEnabled() //这个类中只有一个方法, 只要看看那个方法调用了它,就可以顺藤摸瓜找到最终的调用点
     *
     *   1. spring.factories的加载
     *      顺着思路反向查找,看看是谁在那里调用了 isEnabled函数
     *      selectImports() {
     *          getCandidateConfigurations() {
     *              //getSpringFactoriesLoaderFactoryClass = EnableAutoConfiguration.class
     *              SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
     * 				getBeanClassLoader()){
     *                  //启动过程中有一个硬编码的逻辑,就是会扫描各个包中的对应的文件,并把配置捞取出来
     *                  FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
     *              }
     *          }
     *      }
     *
     *      捞取出来后怎么跟Spring整合呢 ???
     *      或者说 AutoConfigurationImportSelector.serlectImports()后把加载的类又委托给谁继续处理了呢???
     *
     *
     *
     *
     */

}
