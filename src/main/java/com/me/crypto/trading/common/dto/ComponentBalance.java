package com.me.crypto.trading.common.dto;

import com.me.crypto.trading.common.helper.BalanceType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ComponentBalance {
    private BalanceType balanceType;
    private BigDecimal balance;
}
