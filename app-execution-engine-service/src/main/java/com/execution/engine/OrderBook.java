package com.execution.engine;

import com.common.model.Order;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.PriorityQueue;

@Data
@Component
public class OrderBook {
    private PriorityQueue<Order> buyOrders =
            new PriorityQueue<>((a,b)->Double.compare(b.getPrice(), a.getPrice()));

    private PriorityQueue<Order> sellOrders =
            new PriorityQueue<>(Comparator.comparingDouble(Order::getPrice));

    public PriorityQueue<Order> getBuyOrders() {
        return buyOrders;
    }

    public PriorityQueue<Order> getSellOrders() {
        return sellOrders;
    }
}
