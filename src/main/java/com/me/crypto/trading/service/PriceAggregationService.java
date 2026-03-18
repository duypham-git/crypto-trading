package com.me.crypto.trading.service;

import com.me.crypto.trading.common.dto.PriceResponseDto;

import java.util.List;

public interface PriceAggregationService {
    List<PriceResponseDto> getCryptoLatestPrice();
}
