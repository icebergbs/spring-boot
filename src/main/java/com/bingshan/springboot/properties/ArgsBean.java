package com.bingshan.springboot.properties;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 如何访问应用程序变量?
 * ApplicationArguments接口
 */
@Component
public class ArgsBean {

    @Resource
    private ApplicationArguments arguments;

    public void printArgs() {
        System.out.println("# 非选项参数数量: " + arguments.getNonOptionArgs().size());
        System.out.println("# 选项参数数量: " + arguments.getOptionNames().size());
        System.out.println("# 非选项参数具体参数: ");
        arguments.getNonOptionArgs().forEach(System.out::println);

        System.out.println("# 选项参数具体参数: ");
        arguments.getOptionNames().forEach(optionName -> {
            System.out.println("--" + optionName + "=" + arguments.getOptionValues(optionName));
        });
    }
}
