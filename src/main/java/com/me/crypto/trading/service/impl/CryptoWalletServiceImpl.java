package com.me.crypto.trading.service.impl;

import com.me.crypto.trading.common.dto.*;
import com.me.crypto.trading.common.entity.CryptoTransactionEntity;
import com.me.crypto.trading.common.entity.CryptoWalletEntity;
import com.me.crypto.trading.common.entity.PriceAggregationEntity;
import com.me.crypto.trading.common.exception.BadRequestException;
import com.me.crypto.trading.common.exception.DuplicateException;
import com.me.crypto.trading.common.helper.*;
import com.me.crypto.trading.repository.CryptoTransactionRepository;
import com.me.crypto.trading.repository.CryptoTransactionRequestRepository;
import com.me.crypto.trading.repository.CryptoWalletRepository;
import com.me.crypto.trading.repository.PriceAggregationRepository;
import com.me.crypto.trading.service.BaseService;
import com.me.crypto.trading.service.CryptoWalletService;
import com.me.crypto.trading.service.RequestUniquenessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CryptoWalletServiceImpl extends BaseService implements CryptoWalletService {
    @Autowired private CryptoWalletRepository cryptoWalletRepository;
    @Autowired private PriceAggregationRepository priceAggregationRepository;
    @Autowired private CryptoTransactionRepository cryptoTransactionRepository;
    @Autowired private CryptoTransactionRequestRepository cryptoTransactionRequestRepository;
    @Autowired private RequestUniquenessService requestUniquenessService;

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

    @Transactional
    @Override
    public TransferResponseDto transferCrypto(TransferRequestDto request) {
        log.trace("Transfer crypto. request={}", request);
        try {
            //pre-condition
            if (request.getRequestId() == null) throw new BadRequestException("requestId cannot be NULL");
            if (request.getQuantity() == null || request.getQuantity().compareTo(BigDecimal.ZERO) <= 0)
                throw new BadRequestException("quantity cannot be NULL or <= 0");
            if (request.getUserId() == null) throw new BadRequestException("userId cannot be NULL");
            //check if requestId exists
            try {
                requestUniquenessService.checkIfRequestIdExists(request);
            } catch (DuplicateException e) {
                log.trace("Transfer crypto request is duplicated - return cached response. request={}", request);
                return new TransferResponseDto().setTransferStatus(TransferStatus.CREATED);
            }

            //check the current best price of request.symbol
            PriceAggregationEntity priceAggregationEntity = priceAggregationRepository.findBySymbol(request.getSymbol());
            if (priceAggregationEntity == null) {
                log.error("Fail to transfer crypto - priceAggregation not available. request={}", request);
                throw new IllegalStateException("priceAggregation not available");
            }
            //if side is BUY, get bestAskPrice
            //if side is SELL, get bestBidPrice
            BigDecimal transferPrice;
            if (Side.BUY.equals(request.getSide())) {
                transferPrice = priceAggregationEntity.getBestAskPrice();
            } else {
                transferPrice = priceAggregationEntity.getBestBidPrice();
            }
            //totalAmount = quantity * transferPrice
            BigDecimal totalAmount = request.getQuantity().multiply(transferPrice);
            //find the correct transfer balanceType
            BalanceType balanceType = Symbol.ETHUSDT.equals(request.getSymbol()) ? BalanceType.ETH : BalanceType.BTC;
            //fetch crypto wallet
            CryptoWalletEntity usdtCryptoWallet = cryptoWalletRepository.findByUserIdAndBalanceType(request.getUserId(), BalanceType.USDT);
            CryptoWalletEntity transferCryptoWallet = cryptoWalletRepository.findByUserIdAndBalanceType(request.getUserId(), balanceType);
            //if side is BUY, subtract totalAmount from usdtCryptoWallet - add quantity crypto to transferCryptoWallet
            //if side is SELL, add totalAmount to usdtCryptoWallet - subtract quantity crypto from transferCryptoWallet
            if (Side.BUY.equals(request.getSide())) {
                if (usdtCryptoWallet.getBalance().compareTo(totalAmount) < 0) {
                    log.error("Fail to transfer crypto - insufficient usdt amount. request={} usdt.balance={}",
                            request, usdtCryptoWallet.getBalance());
                    throw new IllegalStateException("insufficient usdt amount");
                }
                usdtCryptoWallet.setBalance(usdtCryptoWallet.getBalance().subtract(totalAmount));
                transferCryptoWallet.setBalance(transferCryptoWallet.getBalance().add(request.getQuantity()));
            } else {
                if (transferCryptoWallet.getBalance().compareTo(request.getQuantity()) < 0) {
                    log.error("Fail to transfer crypto - insufficient {} amount. request={} {}.balance={}",
                            balanceType, request, balanceType, transferCryptoWallet.getBalance());
                    throw new IllegalStateException("insufficient " + balanceType + " amount");
                }
                usdtCryptoWallet.setBalance(usdtCryptoWallet.getBalance().add(totalAmount));
                transferCryptoWallet.setBalance(transferCryptoWallet.getBalance().subtract(request.getQuantity()));
            }
            cryptoWalletRepository.save(usdtCryptoWallet);
            cryptoWalletRepository.save(transferCryptoWallet);
            //build transaction record
            createCryptoTransactionRecord(request.getUserId(), request.getSymbol(), request.getSide(),
                    request.getQuantity(), transferPrice, totalAmount);

            return new TransferResponseDto().setTransferStatus(TransferStatus.SUCCESS);
        } catch (Exception e) {
            log.error("Fail to transfer crypto. request={} errorMessage={}", request, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<CryptoTransactionDto> getCryptoTransactions(Long userId){
        log.trace("Get crypto transactions. userId={}", userId);
        try {
            //TODO we can apply pagination here in case the list is too long
            List<CryptoTransactionEntity> cryptoTransactionEntityList = cryptoTransactionRepository.findByUserIdOrderByCreatedAtDesc(userId);
            List<CryptoTransactionDto> cryptoTransactionDtoList = mapFrom(cryptoTransactionEntityList);
            log.trace("Successfully get crypto transactions. userId={} txnSize={}", userId, cryptoTransactionDtoList.size());
            return cryptoTransactionDtoList;
        } catch (Exception e) {
            log.error("Fail to get crypto transactions. userId={} errorMessage={}", userId, e.getMessage(), e);
            throw e;
        }
    }

    protected void createCryptoTransactionRecord(Long userId, Symbol symbol, Side side,
                                                 BigDecimal quantity, BigDecimal price, BigDecimal totalAmount) {
        CryptoTransactionEntity cryptoTransactionEntity = new CryptoTransactionEntity();
        cryptoTransactionEntity.setTxnCode(Txns.generateTxnCode());
        cryptoTransactionEntity.setUserId(userId);
        cryptoTransactionEntity.setSymbol(symbol);
        cryptoTransactionEntity.setSide(side);
        cryptoTransactionEntity.setQuantity(quantity);
        cryptoTransactionEntity.setPrice(price);
        cryptoTransactionEntity.setTotalAmount(totalAmount);
        cryptoTransactionEntity.setCreatedAt(LocalDateTime.now());

        cryptoTransactionRepository.save(cryptoTransactionEntity);
    }

    protected CryptoTransactionDto mapFrom(CryptoTransactionEntity entity) {
        return modelMapper.map(entity, CryptoTransactionDto.class);
    }

    protected List<CryptoTransactionDto> mapFrom(List<CryptoTransactionEntity> entityList) {
        if (entityList == null || entityList.isEmpty()) return new ArrayList<>();
        return entityList
                .stream()
                .map(this::mapFrom)
                .toList();
    }
}
