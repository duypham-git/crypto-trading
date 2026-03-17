package com.me.crypto.trading.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class HuobiTickersResponse {
    private List<HuobiTickerDto> data;
}
