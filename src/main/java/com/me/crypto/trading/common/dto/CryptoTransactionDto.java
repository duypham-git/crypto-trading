package com.me.crypto.trading.common.dto;

import com.me.crypto.trading.common.helper.Side;
import com.me.crypto.trading.common.helper.Symbol;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CryptoTransactionDto {
    private Long id;
    private String txnCode;
    private Long userId;
    private Symbol symbol;
    private Side side;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}
