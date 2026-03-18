package com.me.crypto.trading.common.entity;

import com.me.crypto.trading.common.helper.Side;
import com.me.crypto.trading.common.helper.Symbol;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "crypto_transaction")
public class CryptoTransactionEntity extends BaseAutoIncrementNumberIDEntity {
    private String txnCode;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private Symbol symbol;

    @Enumerated(EnumType.STRING)
    private Side side;

    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
}
