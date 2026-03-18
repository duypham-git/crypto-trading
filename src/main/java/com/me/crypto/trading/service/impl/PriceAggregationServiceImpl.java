package com.me.crypto.trading.service.impl;

import com.me.crypto.trading.common.dto.BinanceBookTickerDto;
import com.me.crypto.trading.common.dto.HuobiTickerDto;
import com.me.crypto.trading.common.dto.HuobiTickersResponse;
import com.me.crypto.trading.common.entity.PriceAggregationEntity;
import com.me.crypto.trading.common.helper.Symbol;
import com.me.crypto.trading.repository.PriceAggregationRepository;
import com.me.crypto.trading.service.BaseService;
import com.me.crypto.trading.service.CryptoTickerHttpService;
import com.me.crypto.trading.service.PriceAggregationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PriceAggregationServiceImpl extends BaseService implements PriceAggregationService {
    @Autowired private PriceAggregationRepository priceAggregationRepository;
    @Autowired private CryptoTickerHttpService cryptoTickerHttpService;

    @Transactional
    @Scheduled(fixedRateString = "${scheduler.run-price-aggregation-fixed-rate}")
    public void runPriceAggregation() {
        log.trace("Run price-aggregation");
        try {
            //TODO can check Redis Distributed Lock here to make running on multiple-instance safe.
            //  - I don't add Redis here since the requirement is using in memory H2, so I guess we want to run this application without any dependencies

            // get binance ticker
            List<BinanceBookTickerDto> binanceBookTickerDtoList = cryptoTickerHttpService.getBinanceTicker();
            // get huobi ticker
            HuobiTickersResponse huobiTickersResponse = cryptoTickerHttpService.getHoubiTicker();
            // map BTCUSDT and ETHUSDT from both responses
            Map<Symbol, BinanceBookTickerDto> binanceBookTickerDtoMap = binanceBookTickerDtoList.stream()
                    .filter(binanceBookTickerDto ->
                                Symbol.ETHUSDT.toString().equalsIgnoreCase(binanceBookTickerDto.getSymbol())
                                    || Symbol.BTCUSDT.toString().equalsIgnoreCase(binanceBookTickerDto.getSymbol())
                            )
                    .collect(Collectors.toMap((BinanceBookTickerDto binanceBookTickerDto1)
                            -> Symbol.valueOf(binanceBookTickerDto1.getSymbol()), binanceBookTickerDto -> binanceBookTickerDto));
            Map<Symbol, HuobiTickerDto> huobiTickerDtoMap = huobiTickersResponse.getData().stream()
                    .filter(huobiTickerDto ->
                                Symbol.ETHUSDT.toString().equalsIgnoreCase(huobiTickerDto.getSymbol())
                                    || Symbol.BTCUSDT.toString().equalsIgnoreCase(huobiTickerDto.getSymbol())
                            )
                    .collect(Collectors.toMap(huobiTickerDto
                            -> Symbol.valueOf(huobiTickerDto.getSymbol().toUpperCase()), huobiTickerDto -> huobiTickerDto));
            //save ETHUSDT and BTCUSDT to database
            for (Symbol symbol : Symbol.values()) {
                BinanceBookTickerDto binanceBookTickerDto = binanceBookTickerDtoMap.get(symbol);
                HuobiTickerDto huobiTickerDto = huobiTickerDtoMap.get(symbol);

                BigDecimal binanceBid = new BigDecimal(binanceBookTickerDto.getBidPrice());
                BigDecimal binanceAsk = new BigDecimal(binanceBookTickerDto.getAskPrice());
                BigDecimal huobiBid = new BigDecimal(huobiTickerDto.getBid());
                BigDecimal huobiAsk = new BigDecimal(huobiTickerDto.getAsk());

                BigDecimal bestBid = binanceBid.max(huobiBid);
                BigDecimal bestAsk = binanceAsk.min(huobiAsk);

                PriceAggregationEntity priceAggregationEntity = priceAggregationRepository.findBySymbol(symbol);
                if (priceAggregationEntity == null) priceAggregationEntity = new PriceAggregationEntity();

                priceAggregationEntity.setSymbol(symbol);
                priceAggregationEntity.setBestBidPrice(bestBid);
                priceAggregationEntity.setBestAskPrice(bestAsk);
                priceAggregationEntity.setUpdatedAt(LocalDateTime.now());

                priceAggregationRepository.save(priceAggregationEntity);
            }
            log.trace("Successfully run price-aggregation");
        } catch (Exception e) {
            log.error("Fail to run price-aggregation. errorMessage={}", e.getMessage(), e);
        }
    }
}
