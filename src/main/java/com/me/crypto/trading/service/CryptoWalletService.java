package com.me.crypto.trading.service;

import com.me.crypto.trading.common.dto.CryptoWalletBalance;

public interface CryptoWalletService {
    CryptoWalletBalance getCryptoWalletBalance(Long userId);
}
