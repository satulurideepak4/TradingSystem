package com.execution.consumer;

import com.common.model.OrderCreatedEvent;
import com.common.model.Trade;
import com.execution.engine.MatchingEngine;
import com.execution.producer.TradeKafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OrderKafkaConsumer {
    @Autowired
    private MatchingEngine engine;
    @Autowired
    private TradeKafkaProducer producer;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics="orders-topic",groupId = "trading-system-poc")
    public void consume(String data){
        OrderCreatedEvent orderCreatedEvent = null;
        try {
            orderCreatedEvent = objectMapper.readValue(data, OrderCreatedEvent.class);
            List<Trade> trades = engine.processOrder(orderCreatedEvent.getOrder());

            trades.forEach(producer::publish);
        } catch (JsonProcessingException e) {
            log.error("Error occurred while converting data : {}",e.getMessage());
        }


    }
}
