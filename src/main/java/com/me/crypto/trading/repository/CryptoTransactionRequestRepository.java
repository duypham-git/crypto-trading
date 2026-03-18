package com.me.crypto.trading.repository;

import com.me.crypto.trading.common.entity.CryptoTransactionRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoTransactionRequestRepository extends JpaRepository<CryptoTransactionRequestEntity, Long> {
    CryptoTransactionRequestEntity findByRequestId(String requestId);
}
