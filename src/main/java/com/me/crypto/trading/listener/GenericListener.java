package com.me.crypto.trading.listener;

import com.me.crypto.trading.common.entity.CryptoWalletEntity;
import com.me.crypto.trading.common.helper.BalanceType;
import com.me.crypto.trading.repository.CryptoWalletRepository;
import com.me.crypto.trading.service.CryptoTickerHttpService;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Component
public class GenericListener {
    @Autowired private CryptoWalletRepository cryptoWalletRepository;

    @Transactional
    @EventListener
    public void appReadyListener1(ApplicationReadyEvent event) {
        //init data for crypto wallet
        long userId = 999;
        //USDT
        CryptoWalletEntity entity = new CryptoWalletEntity();
        entity.setUserId(userId);
        entity.setBalance(BigDecimal.valueOf(50000));
        entity.setBalanceType(BalanceType.USDT);
        entity.setUpdatedAt(LocalDateTime.now());
        cryptoWalletRepository.save(entity);
        //ETH
        entity = new CryptoWalletEntity();
        entity.setUserId(userId);
        entity.setBalance(BigDecimal.ZERO);
        entity.setBalanceType(BalanceType.ETH);
        entity.setUpdatedAt(LocalDateTime.now());
        cryptoWalletRepository.save(entity);
        //BTC
        entity = new CryptoWalletEntity();
        entity.setUserId(userId);
        entity.setBalance(BigDecimal.ZERO);
        entity.setBalanceType(BalanceType.BTC);
        entity.setUpdatedAt(LocalDateTime.now());
        cryptoWalletRepository.save(entity);
    }
}
