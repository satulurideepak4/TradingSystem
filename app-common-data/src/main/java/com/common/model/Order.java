package com.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;
    private String userId;
    private String symbol;
    private OrderSide side;
    private double price;
    private int quantity;
    private long orderPlacedTime;
}
