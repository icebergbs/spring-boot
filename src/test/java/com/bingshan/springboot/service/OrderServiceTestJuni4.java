package com.bingshan.springboot.service;

import com.bingshan.springboot.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @RunWith :
 *  该注解用于说明此测试类的运行者,比如示例中使用的 SpringRunner
 * SpringRunner:
 *  是由spring-test提供的, 它史记上继承了 SpringJUnit4ClassRunner类,并为重新定义任何方法,可以理解为一个简洁的名字
 * @SpringBootTest:
 *  由Spring Boot 提供, 该注解为SpringApplication创建上下文并支持Spring Boot特性
 *
 * 项目须引入spring-boot-starter-test, 默认情况下此依赖使用的单元测试类库 JUnit4,
 * 此时@SpringbootTest需要配合@RunWith(SpringRunner.class)使用,否则注解会被忽略
 * <dependency>
 *             <groupId>junit</groupId>
 *             <artifactId>junit</artifactId>
 *             <scope>test</scope>
 *         </dependency>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTestJuni4 {

    @Autowired
    private OrderService orderService;

    @Test
    public void testInsert() {
        Order order = new Order();
        order.setOrderNo("A001");
        order.setUserId(100);
        orderService.insert(order);
    }
}
