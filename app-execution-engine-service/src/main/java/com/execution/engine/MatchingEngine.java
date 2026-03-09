package com.execution.engine;

import com.common.model.Order;
import com.common.model.OrderSide;
import com.common.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MatchingEngine {
    @Autowired
    private OrderBook orderBook ;

    public List<Trade> processOrder(Order order) {

        List<Trade> trades = new ArrayList<>();

        if(order.getSide() == OrderSide.BUY){

            while(!orderBook.getSellOrders().isEmpty()
                    && orderBook.getSellOrders().peek().getPrice() <= order.getPrice()){

                Order sell = orderBook.getSellOrders().poll();

                int qty = Math.min(order.getQuantity(), sell.getQuantity());

                Trade trade = new Trade();
                trade.setTradeId(UUID.randomUUID().toString());
                trade.setBuyOrderId(order.getOrderId());
                trade.setSellOrderId(sell.getOrderId());
                trade.setPrice(sell.getPrice());
                trade.setQuantity(qty);
                trade.setTimestamp(System.currentTimeMillis());

                trades.add(trade);

                order.setQuantity(order.getQuantity() - qty);
                sell.setQuantity(sell.getQuantity() - qty);

                if(sell.getQuantity() > 0)
                    orderBook.getSellOrders().add(sell);

                if(order.getQuantity()==0)
                    break;
            }

            if(order.getQuantity()>0)
                orderBook.getBuyOrders().add(order);
        }

        else{

            while(!orderBook.getBuyOrders().isEmpty()
                    && orderBook.getBuyOrders().peek().getPrice() >= order.getPrice()){

                Order buy = orderBook.getBuyOrders().poll();

                int qty = Math.min(order.getQuantity(), buy.getQuantity());

                Trade trade = new Trade();
                trade.setTradeId(UUID.randomUUID().toString());
                trade.setBuyOrderId(buy.getOrderId());
                trade.setSellOrderId(order.getOrderId());
                trade.setPrice(order.getPrice());
                trade.setQuantity(qty);
                trade.setTimestamp(System.currentTimeMillis());

                trades.add(trade);

                order.setQuantity(order.getQuantity() - qty);
                buy.setQuantity(buy.getQuantity() - qty);

                if(buy.getQuantity()>0)
                    orderBook.getBuyOrders().add(buy);

                if(order.getQuantity()==0)
                    break;
            }

            if(order.getQuantity()>0)
                orderBook.getSellOrders().add(order);
        }

        return trades;
    }
}
