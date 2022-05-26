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
     */

    @Test
    void contextLoads() {
    }

}
