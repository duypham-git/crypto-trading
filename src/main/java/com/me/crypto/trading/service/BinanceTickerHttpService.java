package com.me.crypto.trading.service;

import com.me.crypto.trading.common.dto.BinanceBookTickerDto;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface BinanceTickerHttpService {
    @GET("/api/v3/ticker/bookTicker")
    Call<List<BinanceBookTickerDto>> getBinanceTicker();
}
