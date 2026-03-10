package com.order.producer;

import com.common.model.Order;
import com.common.model.OrderCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void publish(Order order){
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setOrder(order);
        try {
            String data = objectMapper.writeValueAsString(orderCreatedEvent);
            kafkaTemplate.send("orders-topic", order.getSymbol(), data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}
