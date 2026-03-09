package com.order.service;

import com.common.model.Order;
import com.order.producer.OrderKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderKafkaProducer orderKafkaProducer;

    public void createOrder(Order order){
        order.setOrderPlacedTime(System.currentTimeMillis());
        orderKafkaProducer.publish(order);
    }
}
