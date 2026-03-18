package com.me.crypto.trading.repository;

import com.me.crypto.trading.common.entity.PriceAggregationEntity;
import com.me.crypto.trading.common.helper.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceAggregationRepository extends JpaRepository<PriceAggregationEntity, Long> {
    PriceAggregationEntity findBySymbol(Symbol symbol);
}
