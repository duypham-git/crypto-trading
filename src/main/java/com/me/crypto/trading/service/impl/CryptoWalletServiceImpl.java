package com.me.crypto.trading.service.impl;

import com.me.crypto.trading.common.dto.ComponentBalanceDto;
import com.me.crypto.trading.common.dto.CryptoWalletBalanceDto;
import com.me.crypto.trading.common.entity.CryptoWalletEntity;
import com.me.crypto.trading.repository.CryptoWalletRepository;
import com.me.crypto.trading.service.BaseService;
import com.me.crypto.trading.service.CryptoWalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CryptoWalletServiceImpl extends BaseService implements CryptoWalletService {
    @Autowired private CryptoWalletRepository cryptoWalletRepository;

    @Override
    public CryptoWalletBalanceDto getCryptoWalletBalance(Long userId){
        log.trace("Get crypto wallet balance. userId={}", userId);
        try {
            List<CryptoWalletEntity> cryptoWalletEntityList = cryptoWalletRepository.findByUserId(userId);
            List<ComponentBalanceDto> componentBalanceList = cryptoWalletEntityList
                    .stream()
                    .map(cryptoWalletEntity -> {
                        ComponentBalanceDto componentBalance = new ComponentBalanceDto();
                        componentBalance.setBalanceType(cryptoWalletEntity.getBalanceType());
                        componentBalance.setBalance(cryptoWalletEntity.getBalance());
                        return componentBalance;
                    })
                    .toList();

            CryptoWalletBalanceDto cryptoWalletBalance = new CryptoWalletBalanceDto();
            cryptoWalletBalance.setBalanceList(componentBalanceList);
            log.trace("Successfully get crypto wallet balance. userId={} balance={}", userId, cryptoWalletBalance);
            return cryptoWalletBalance;
        } catch (Exception e) {
            log.error("Fail to get crypto wallet balance. userId={} errorMessage={}",
                    userId, e.getMessage(), e);
            throw e;
        }
    }
}
