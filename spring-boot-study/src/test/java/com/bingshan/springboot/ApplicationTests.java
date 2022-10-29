package com.bingshan.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    /**
     * Sprint Boot 单元测试
     *
     * 13.1 Spring boot 对单元测试的支持
     *
     *  一系列注解和工具的集成,通过两个项目提供:
     *      spring-boot-test 核心功能,类库
     *          JUnit: 一个Java语言的单元测试框架
     *          Spring Test & Spring Boot Test: 为Spring Boot 应用提供集成测试和工具支持
     *          AssertJ: 支持流式断言的Java测试框架
     *          Hamcrest: 一个匹配器库
     *          Mockito: 一个Java Mock框架
     *          JSONNassert: 一个针对JSON的断言库
     *          JsonPath: 一个JSON XPath库
     *
     *      spring-boot-test-autoconfigure 支持自动配置
     *
     * 13.2 常用单元测试注解
     *  Spring Boot提供的:
     * @SpringBootTest  @MockBean  @SpyBean  @WebMvcTest  @AutoConfigureMockMvc
     *  Junit提供的:
     * @RunWith
     *
     * 13.4 Web应用单元测试
     *  在面向对象的程序设计中, 模拟对象(mock object) 是以可控的方式模拟真实对象行为的假对象.
     *  在编程过程中, 通常通过模拟一些输入数据,来验证程序是否达到预期效果.
     *  模拟对象一般应用真实对象有以下特性的场景:
     *      行为不确定
     *      真实环境难搭建
     *      行为难触发
     *      速度很慢
     *      需要界面操作
     *      回调机制等
     *  对Controller 层进行单元测试时, 便需要使用模拟对象, 采用 spring-test包中提供的 MockMvc.
     *
     *  MockMvc 实现了对Http请求的模拟,能够直接使用网络的形式, 转换到 Controller的调用,这样可以使得测试速度快
     *  MockMvc使用, 创建一个简单的 TestController  hell()
     */

    @Test
    void contextLoads() {
    }

}
