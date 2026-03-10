package com.execution.producer;

import com.common.model.Trade;
import com.common.model.TradeExecutedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TradeKafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void publish(Trade trade){

        try {
            TradeExecutedEvent tradeExecutedEvent = new TradeExecutedEvent(trade);
            String data = objectMapper.writeValueAsString(tradeExecutedEvent);
            kafkaTemplate.send("trade-topic", data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
