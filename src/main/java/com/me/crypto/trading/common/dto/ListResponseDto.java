package com.me.crypto.trading.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListResponseDto<T> {
    private List<T> items;
    private Integer size;
    private Integer nextIndex;
    private Integer nextOffset;
}
