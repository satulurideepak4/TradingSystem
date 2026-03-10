package com.register.consumer;

import com.common.model.Trade;
import com.common.model.TradeExecutedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.register.entity.TradeEntity;
import com.register.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TradeKafkaConsumer {
    @Autowired
    private TradeRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics="trade-topic",groupId = "trading-system-poc")
    public void consume(String data){
        TradeExecutedEvent tradeExecutedEvent = null;
        try {
            tradeExecutedEvent = objectMapper.readValue(data, TradeExecutedEvent.class);
            Trade t = tradeExecutedEvent.getTrade();

            TradeEntity e = new TradeEntity();
            e.setTradeId(t.getTradeId());
            e.setBuyOrderId(t.getBuyOrderId());
            e.setSellOrderId(t.getSellOrderId());
            e.setPrice(t.getPrice());
            e.setQuantity(t.getQuantity());
            e.setTimestamp(t.getTimestamp());

            repository.save(e);
        } catch (JsonProcessingException e) {
            log.error("Error occurred while converting data : {}",e.getMessage());
        }

    }
}
