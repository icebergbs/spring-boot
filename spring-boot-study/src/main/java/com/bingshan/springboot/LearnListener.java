package com.bingshan.springboot;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

//需对该类进行Bean的实例化

/**
 * 实现 ApplicationListener 并监听ContextRefreshedEvent事件
 * 当容器创建或 刷新时,该监听器的 onApplicationEvent方法会被调用
 *
 * 在具体的实战业务中, 可以自定义事件, 在完成业务子后手动触发对应的事件监听器,
 * 也就是手动调用ApplicationContext的publishEvent(ApplicationEvent event)
 */
@Component
public class LearnListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //打印容器中初始Bean的数量
        System.out.println("监听器获得容器中初始化Bean数量: " + event.getApplicationContext().getBeanDefinitionCount());
    }
}
