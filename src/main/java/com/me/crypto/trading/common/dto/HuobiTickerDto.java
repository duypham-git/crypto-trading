package com.me.crypto.trading.common.dto;

import lombok.Data;

@Data
public class HuobiTickerDto {
    private String symbol;
    private String bid;
    private String ask;
}
