package com.me.crypto.trading.common.dto;

import com.me.crypto.trading.common.helper.Side;
import com.me.crypto.trading.common.helper.Symbol;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDto {
    private Long userId;
    private String requestId;
    private Symbol symbol;
    private Side side;
    private BigDecimal quantity;
}
