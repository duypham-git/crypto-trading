package com.me.crypto.trading.service;

import com.me.crypto.trading.common.dto.CryptoWalletBalanceDto;
import com.me.crypto.trading.common.dto.TransferRequestDto;
import com.me.crypto.trading.common.dto.TransferResponseDto;

public interface CryptoWalletService {
    CryptoWalletBalanceDto getCryptoWalletBalance(Long userId);

    TransferResponseDto transferCrypto(TransferRequestDto request);
}
