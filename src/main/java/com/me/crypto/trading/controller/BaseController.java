package com.me.crypto.trading.controller;

import com.me.crypto.trading.common.dto.ResponseBodyDto;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import java.util.concurrent.Callable;

public abstract class BaseController {
    @SneakyThrows
    public ResponseBodyDto mapResponse(Callable callable){
        //use callable.call() to get result
        Object result = callable.call();

        //build ResponseBodyDto
        ResponseBodyDto responseBodyDto = new ResponseBodyDto();
        responseBodyDto.setCode(HttpStatus.OK.value());
        responseBodyDto.setMessage(HttpStatus.OK.getReasonPhrase());
        responseBodyDto.setData(result);
        return responseBodyDto;
    }
}
