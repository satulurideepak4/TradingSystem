package com.register.consumer;

import com.common.model.Trade;
import com.common.model.TradeExecutedEvent;
import com.register.entity.TradeEntity;
import com.register.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TradeKafkaConsumer {
    @Autowired
    private TradeRepository repository;

    @KafkaListener(topics="trade-topic")
    public void consume(TradeExecutedEvent event){

        Trade t = event.getTrade();

        TradeEntity e = new TradeEntity();
        e.setTradeId(t.getTradeId());
        e.setBuyOrderId(t.getBuyOrderId());
        e.setSellOrderId(t.getSellOrderId());
        e.setPrice(t.getPrice());
        e.setQuantity(t.getQuantity());
        e.setTimestamp(t.getTimestamp());

        repository.save(e);
    }
}
