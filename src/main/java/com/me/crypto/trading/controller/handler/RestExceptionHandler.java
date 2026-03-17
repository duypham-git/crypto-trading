package com.me.crypto.trading.controller.handler;

import com.me.crypto.trading.common.dto.ResponseBodyDto;
import com.me.crypto.trading.common.exception.BadRequestException;
import com.me.crypto.trading.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBodyDto> handleException(Exception e, WebRequest request) {
        log.error("Failed to execute request. errMessage={}", e.getMessage(), e);
        ResponseBodyDto responseBodyDto = new ResponseBodyDto();
        responseBodyDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseBodyDto.setMessage(e.getMessage());

        return new ResponseEntity<>(responseBodyDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseBodyDto> handleBaseException(BaseException e, WebRequest request) {
        log.error("Failed to execute request. errMessage={}", e.getMessage(), e);
        ResponseBodyDto responseBodyDto = new ResponseBodyDto();
        responseBodyDto.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseBodyDto.setMessage(e.getMessage());
        responseBodyDto.setErrorData(e.getErrorData());

        return new ResponseEntity<>(responseBodyDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseBodyDto> handleResourceNotFoundException(BadRequestException e, WebRequest request) {
        log.error("Failed to execute request. errMessage={}", e.getMessage(), e);
        ResponseBodyDto responseBodyDto = new ResponseBodyDto();
        responseBodyDto.setCode(HttpStatus.BAD_REQUEST.value());
        responseBodyDto.setMessage(e.getMessage());
        responseBodyDto.setErrorData(e.getErrorData());

        return new ResponseEntity<>(responseBodyDto, HttpStatus.BAD_REQUEST);
    }
}
