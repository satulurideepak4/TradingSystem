package com.register.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="trades")
@Data
public class TradeEntity {

    @Id
    private String tradeId;

    private String buyOrderId;
    private String sellOrderId;

    private double price;
    private int quantity;

    private long timestamp;
}
