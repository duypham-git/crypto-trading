package com.me.crypto.trading.service;

import com.me.crypto.trading.common.dto.HuobiTickersResponseDto;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HoubiTickerHttpService {
    @GET("/market/tickers")
    Call<HuobiTickersResponseDto> getHoubiTicker();
}
