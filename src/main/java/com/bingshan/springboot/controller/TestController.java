package com.bingshan.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/mock")
    public String mock(String name) {
        return "Hello " + name + "!";
    }
}
