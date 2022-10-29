package com.bingshan.springboot.junit;

import org.junit.jupiter.api.*;

/**
 *   一个典型的测试类结构:
 */
//指定用例在测试报告中的名称
@DisplayName("售票器类型测试")
public class TicketSellerTest {
    //定义待测试的实例
    private TicketSeller ticketSeller = new TicketSeller();

    //定义在整个测试类开始前执行的操作
    //通常包括全局和外部资源的创建和初始化
    @BeforeAll
    public static void init() {

    }

    //定义在整个测试类完成后执行的操作
    //通常包括全局和外部资源的释放和销毁
    @AfterAll
    public static void cleanup() {

    }

    //定义在整个测试类开始前执行的操作
    //通常包括基础数据和运行环境的准备
    @BeforeEach
    void setUp() {
    }

    //定义在整个测试类完成后执行的操作
    //通常包括运行环境的清理
    @AfterEach
    void tearDown() {
    }

    //测试用例
    @Test
    @Tag("fast")
    @DisplayName("售票后与票应减少")
    public void shouldReduceInventoryWhenTicketSoldOut() {

    }

    // Disabled 注释将禁用测试用例
    // 该测试用例将会出现在最终的报告中, 但不会被执行
    @Disabled
    @Tag("slow")
    @Test
    @DisplayName("有退票时余票应增加")
    public void shouldInvreaseInventoryWhenTicketRefund() {

    }

}