package com.me.crypto.trading.common.entity;

import com.me.crypto.trading.common.helper.BalanceType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "crypto_wallet")
public class CryptoWalletEntity extends BaseAutoIncrementNumberIDEntity {
    private Long userId;

    @Enumerated(EnumType.STRING)
    private BalanceType balanceType;

    private BigDecimal balance;
    private LocalDateTime updatedAt;
}
