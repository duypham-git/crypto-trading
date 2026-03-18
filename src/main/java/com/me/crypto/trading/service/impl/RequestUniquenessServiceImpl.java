package com.me.crypto.trading.service.impl;

import com.me.crypto.trading.common.dto.TransferRequestDto;
import com.me.crypto.trading.common.entity.CryptoTransactionRequestEntity;
import com.me.crypto.trading.common.exception.DuplicateException;
import com.me.crypto.trading.repository.CryptoTransactionRequestRepository;
import com.me.crypto.trading.service.BaseService;
import com.me.crypto.trading.service.RequestUniquenessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class RequestUniquenessServiceImpl extends BaseService implements RequestUniquenessService {
    @Autowired private CryptoTransactionRequestRepository cryptoTransactionRequestRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void checkIfRequestIdExists(TransferRequestDto request) {
        CryptoTransactionRequestEntity cryptoTransactionRequestEntity = cryptoTransactionRequestRepository.findByRequestId(request.getRequestId());
        if (cryptoTransactionRequestEntity != null) {
            throw new DuplicateException("transfer crypto request is duplicated");
        } else {
            cryptoTransactionRequestEntity = new CryptoTransactionRequestEntity();
            cryptoTransactionRequestEntity.setRequestId(request.getRequestId());
            cryptoTransactionRequestEntity.setUserId(request.getUserId());
            cryptoTransactionRequestEntity.setCreatedAt(LocalDateTime.now());
            try {
                cryptoTransactionRequestRepository.save(cryptoTransactionRequestEntity);
            } catch (Exception e) {
                if (e.getMessage().contains("Duplicate entry")) {
                    throw new DuplicateException("transfer crypto request is duplicated");
                } else {
                    throw e;
                }
            }
        }
    }
}
