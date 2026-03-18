package com.me.crypto.trading.common.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "crypto_transaction_request")
public class CryptoTransactionRequestEntity extends BaseAutoIncrementNumberIDEntity {
    private Long userId;
    private String requestId;
    private LocalDateTime createdAt;
}
