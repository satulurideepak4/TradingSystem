package com.order.producer;

import com.common.model.Order;
import com.common.model.OrderCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class OrderKafkaProducer {
    @Autowired
    private KafkaTemplate<String,OrderCreatedEvent> kafkaTemplate;

    public void publish(Order order){
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setOrder(order);
        kafkaTemplate.send("orders-topic", order.getSymbol(), orderCreatedEvent);
    }

}
