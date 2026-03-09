package com.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trade {
    private String tradeId;
    private String buyOrderId;
    private String sellOrderId;
    private double price;
    private int quantity;
    private long timestamp;
}