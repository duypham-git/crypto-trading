package com.me.crypto.trading.common.dto;

import lombok.Data;

@Data
public class BinanceBookTickerDto {
    private String symbol;
    private String bidPrice;
    private String askPrice;
}
