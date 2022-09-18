package com.bingshan.springboot.starter;

import com.bingshan.springboot.msg.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 测试自定义Starter
 */
@RestController
public class HelloWorldController {

    @Resource
    private MsgService msgService;

    @GetMapping("/sendMsg")
    public String sendMsg() {
        msgService.sendMsg("自定义Start测试消息");
        return "success";
    }
}
