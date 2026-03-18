package com.me.crypto.trading.controller;

import com.me.crypto.trading.common.dto.ResponseBodyDto;
import com.me.crypto.trading.service.CryptoWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoController extends BaseController {
    @Autowired private CryptoWalletService cryptoWalletService;

    @GetMapping("/v1/crypto/wallet/balance")
    @ResponseBody
    public ResponseBodyDto getCryptoWalletBalance(@RequestHeader(defaultValue = "999") Long userId){
        return mapResponse(() -> cryptoWalletService.getCryptoWalletBalance(userId));
    }
}
