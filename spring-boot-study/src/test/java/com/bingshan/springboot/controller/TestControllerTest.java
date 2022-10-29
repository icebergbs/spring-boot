package com.bingshan.springboot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


/**
 * MockMvc 的使用 , 基于 JUnit4  Springboot 2.x
 *      ..@AutoConfigurreMockMvc 提供了自动配置MockMvc的功能, 因此需要通过@Autowired注入MockMvc即可
 *
 * 单元测试包含以下步骤:
 *      准备测试环境 - 执行MockMvc请求 - 添加验证断言 - 添加结果处理器 - 得到MvcResult进行自定义断言\进行下一步的异步请求 - 卸载测试环境
 *      Web应用的测试还有许多其他内容: 检测Web类型 , 检测测试配置, 排除测试配置及 事务回滚
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@Test
    public void testMock() throws Exception {
        //mockMvc.perform执行一个请求
        mockMvc.perform(MockMvcRequestBuilders
        //MockMvcRequestBuilders.get("xxx") 构造一个请求
                .get("/mock")
                //设置返回值类型为 utf-8, 否则默认为 ISO-8859-1
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                //RestActions.param添加请求传值
                .param("name", "MockMvc"))
                //ResultActions.andExpect 添加执行完成后的断言
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello MockMvc!"))
                //ResultActions.andDo 添加一个结果处理器, 此处打印整个响应结果信息
        .andDo(MockMvcResultHandlers.print());
    }
}