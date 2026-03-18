package com.me.crypto.trading.service;

import com.me.crypto.trading.common.dto.TransferRequestDto;

public interface RequestUniquenessService {
    void checkIfRequestIdExists(TransferRequestDto request);
}
