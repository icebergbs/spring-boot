package com.bingshan.springboot.service.impl;

import com.bingshan.springboot.entity.Order;
import com.bingshan.springboot.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public void insert(Order order) {

        //insert
        LOG.info("insert Order info! order: {}", order);
        return;
    }
}
