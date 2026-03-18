package com.me.crypto.trading.service.impl;

import com.me.crypto.trading.common.dto.ComponentBalance;
import com.me.crypto.trading.common.dto.CryptoWalletBalance;
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
    public CryptoWalletBalance getCryptoWalletBalance(Long userId){
        log.trace("Get crypto wallet balance. userId={}", userId);
        try {
            List<CryptoWalletEntity> cryptoWalletEntityList = cryptoWalletRepository.findByUserId(userId);
            List<ComponentBalance> componentBalanceList = cryptoWalletEntityList
                    .stream()
                    .map(cryptoWalletEntity -> {
                        ComponentBalance componentBalance = new ComponentBalance();
                        componentBalance.setBalanceType(cryptoWalletEntity.getBalanceType());
                        componentBalance.setBalance(cryptoWalletEntity.getBalance());
                        return componentBalance;
                    })
                    .toList();

            CryptoWalletBalance cryptoWalletBalance = new CryptoWalletBalance();
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
