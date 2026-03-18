package com.me.crypto.trading.common.entity;

import com.me.crypto.trading.common.helper.Symbol;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "price_aggregation")
public class PriceAggregationEntity extends BaseAutoIncrementNumberIDEntity {
    @Enumerated(EnumType.STRING)
    private Symbol symbol;

    private BigDecimal bestBidPrice;
    private BigDecimal bestAskPrice;
    private LocalDateTime updatedAt;
}
