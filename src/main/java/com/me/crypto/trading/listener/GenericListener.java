package com.me.crypto.trading.listener;

import com.me.crypto.trading.service.CryptoTickerHttpService;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GenericListener {
    @Autowired private ObservationRegistry observationRegistry;
    @Autowired private RedisTemplate<String, String> redisTemplate;
    @Autowired private RedisLockRegistry redisLockRegistry;
    @Autowired private CryptoTickerHttpService cryptoTickerHttpService;

    @EventListener
    public void appReadyListener1(ApplicationReadyEvent event) {

    }
}
