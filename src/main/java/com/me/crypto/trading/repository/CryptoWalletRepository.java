package com.me.crypto.trading.repository;

import com.me.crypto.trading.common.entity.CryptoWalletEntity;
import com.me.crypto.trading.common.helper.BalanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoWalletRepository extends JpaRepository<CryptoWalletEntity, Long> {
    CryptoWalletEntity findByUserIdAndBalanceType(Long userId, BalanceType balanceType);
    List<CryptoWalletEntity> findByUserId(Long userId);
}
