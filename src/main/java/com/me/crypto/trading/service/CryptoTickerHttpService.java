package com.me.crypto.trading.service;

import com.me.crypto.trading.common.dto.BinanceBookTickerDto;
import com.me.crypto.trading.common.dto.HuobiTickersResponse;

import java.util.List;

public interface CryptoTickerHttpService {
    List<BinanceBookTickerDto> getBinanceTicker();
    HuobiTickersResponse getHoubiTicker();
}
