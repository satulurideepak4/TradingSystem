package com.execution.consumer;

import com.common.model.OrderCreatedEvent;
import com.common.model.Trade;
import com.execution.engine.MatchingEngine;
import com.execution.producer.TradeKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderKafkaConsumer {
    @Autowired
    private MatchingEngine engine;
    @Autowired
    private TradeKafkaProducer producer;


    @KafkaListener(topics="orders-topic")
    public void consume(OrderCreatedEvent event){

        List<Trade> trades = engine.processOrder(event.getOrder());

        trades.forEach(producer::publish);
    }
}
