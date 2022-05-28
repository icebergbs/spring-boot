package com.bingshan.springboot.service;

import com.bingshan.springboot.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 13.3 JUnit5单元测试实例
 *  使用@SpringBootTest 即可, 组合了 @ExtenWith(SpringExtension.class)
 *
 *  虽然单元测试代码与 JUnit4基本相同,但本质还是有区别的.
 *  比如,在使用JUnit5时, 默认的 spring-boot-starter-test 依赖类库已无法满足须引入 junit-jupiter
 *  <!--        Junit5-->
 *         <dependency>
 *             <groupId>org.junit.jupiter</groupId>
 *             <artifactId>junit-jupiter</artifactId>
 *             <version>5.7.2</version>
 *             <scope>test</scope>
 *         </dependency>
 *  同时, 如果有必要将 junit-vintage-engine 进行排除
 *   <dependency>
 *             <groupId>org.springframework.boot</groupId>
 *             <artifactId>spring-boot-starter-test</artifactId>
 *             <scope>test</scope>
 *             <exclusions>
 *                 <exclusion>
 *                     <groupId>org.junit.vintage</groupId>
 *                     <artifactId>junit-vintage-engine</artifactId>
 *                 </exclusion>
 *             </exclusions>
 *         </dependency>
 *
 *  上面代码经常会遇到一个问题, 就是从 JUnit4 升级到 JUnit5, 如果只是把类上注解换了, 会发现通过 @Resource @Autowired 注入的OrderService
 *  空指针异常. 这是为什么??? :
 *      JUnit4生到 JUnit5, @Test注解变了
 *      JUnit4  org.junit.Test
 *      JUnit5  org.junit.jupiter.api.Test
 *
 *  JUnit5最大的变化 @Test注解改为 由几个不同的模块组成,其中包括3个不同的子项目:
 *      JUnit Platform
 *      JUnit Jupiter
 *      JUnit Vintage
 *
 *  JUnit5提供了一套自己的注解:
 *      //@BeforeAll  @BeforeEache @Test @DisplayName  @AfterEach  @AfterAll  @Disable  @ExtendWith
 *
 */

@SpringBootTest
public class OrderServiceTestJuni5 {

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
