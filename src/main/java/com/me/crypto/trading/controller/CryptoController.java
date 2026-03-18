package com.me.crypto.trading.controller;

import com.me.crypto.trading.common.dto.ResponseBodyDto;
import com.me.crypto.trading.service.CryptoWalletService;
import com.me.crypto.trading.service.PriceAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoController extends BaseController {
    @Autowired private CryptoWalletService cryptoWalletService;
    @Autowired private PriceAggregationService priceAggregationService;

    @GetMapping("/v1/crypto/wallet/balance")
    @ResponseBody
    public ResponseBodyDto getCryptoWalletBalance(@RequestHeader(defaultValue = "999") Long userId){
        //TODO normally, userId will be extracted after the token verification
        //  - but since the requirement is assuming user authenticated/authorized, then I will get userId from requestHeader
        //  - default requestHeader userId is 999 so that you don't need to pass it from API
        return mapResponse(() -> cryptoWalletService.getCryptoWalletBalance(userId));
    }

    @GetMapping("/v1/crypto/price/latest")
    @ResponseBody
    public ResponseBodyDto getCryptoLatestPrice(){
        return mapResponse(() -> priceAggregationService.getCryptoLatestPrice());
    }
}
