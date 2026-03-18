package com.me.crypto.trading.common.dto;

import com.me.crypto.trading.common.helper.TransferStatus;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class TransferResponseDto {
    private TransferStatus transferStatus;
}
