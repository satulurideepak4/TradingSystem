package com.register.repository;

import com.register.entity.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository
        extends JpaRepository<TradeEntity,String> {
}
