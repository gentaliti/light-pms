package com.gentaliti.common.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Log4j2
public class ExceptionHandlerAdvice {
    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<ResponseDto> handleNotFound(NotFoundException e) {
        log.warn(e.getMessage(), e);
        ResponseDto response = ResponseDto.builder()
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<ResponseDto> conflictException(IllegalArgumentException e) {
        log.warn(e.getMessage(), e);
        ResponseDto response = ResponseDto.builder()
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ResponseDto> handle500Error(Exception e) {
        log.error(e.getMessage(), e);
        ResponseDto response = ResponseDto.builder()
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Something went wrong with your request. Contact support.")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
