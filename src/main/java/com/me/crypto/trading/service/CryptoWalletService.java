package com.me.crypto.trading.service;

import com.me.crypto.trading.common.dto.CryptoWalletBalanceDto;

public interface CryptoWalletService {
    CryptoWalletBalanceDto getCryptoWalletBalance(Long userId);
}
