package com.me.crypto.trading.common.dto;

import com.me.crypto.trading.common.helper.Symbol;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PriceResponseDto {
    private Symbol symbol;
    private BigDecimal bestBidPrice;
    private BigDecimal bestAskPrice;
    private LocalDateTime updatedAt;
}
