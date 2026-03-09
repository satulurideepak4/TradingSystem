package com.execution.producer;

import com.common.model.Trade;
import com.common.model.TradeExecutedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TradeKafkaProducer {
    @Autowired
    private KafkaTemplate<String, TradeExecutedEvent> kafkaTemplate;

    public void publish(Trade trade){

        kafkaTemplate.send("trade-topic",
                new TradeExecutedEvent(trade));
    }
}
