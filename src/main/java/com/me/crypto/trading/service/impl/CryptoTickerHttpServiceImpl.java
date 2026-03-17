package com.me.crypto.trading.service.impl;

import com.me.crypto.trading.common.dto.BinanceBookTickerDto;
import com.me.crypto.trading.common.dto.HuobiTickersResponse;
import com.me.crypto.trading.service.BaseService;
import com.me.crypto.trading.service.BinanceTickerHttpService;
import com.me.crypto.trading.service.CryptoTickerHttpService;
import com.me.crypto.trading.service.HoubiTickerHttpService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;

@Slf4j
@Service
public class CryptoTickerHttpServiceImpl extends BaseService implements CryptoTickerHttpService {
    @Autowired private BinanceTickerHttpService binanceTickerHttpService;
    @Autowired private HoubiTickerHttpService houbiTickerHttpService;

    @Override
    public List<BinanceBookTickerDto> getBinanceTicker() {
        return execute(binanceTickerHttpService.getBinanceTicker(), "get binance-ticker");
    }

    @Override
    public HuobiTickersResponse getHoubiTicker() {
        return execute(houbiTickerHttpService.getHoubiTicker(), "get houbi-ticker");
    }

    @SneakyThrows
    protected <T> T execute(Call<T> call, String methodMessage) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                log.error("Failed to {}. code={} errMessage={} errBody={}",
                        methodMessage, response.code(), response.message(), response.errorBody());
                return null;
            }
        } catch (Exception e) {
            log.error("Failed to {}. errMessage={}",
                    methodMessage, e.getMessage(), e);
            return null;
        }
    }
}
