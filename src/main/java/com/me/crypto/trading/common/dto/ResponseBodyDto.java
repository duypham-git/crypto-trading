package com.me.crypto.trading.common.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseBodyDto<T> {
    private int code;
    private String message;
    private T data;
    private Map<String, Object> errorData;
}
