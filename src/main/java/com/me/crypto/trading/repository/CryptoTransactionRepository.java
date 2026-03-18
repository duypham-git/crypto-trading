package com.me.crypto.trading.repository;

import com.me.crypto.trading.common.entity.CryptoTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CryptoTransactionRepository extends JpaRepository<CryptoTransactionEntity, Long> {
    List<CryptoTransactionEntity> findByUserId(Long userId);
}
