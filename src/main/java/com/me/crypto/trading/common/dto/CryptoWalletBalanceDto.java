package com.me.crypto.trading.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class CryptoWalletBalanceDto {
    private List<ComponentBalanceDto> balanceList;
}
