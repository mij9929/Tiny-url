package com.example.tinyurl.exception;

import com.example.tinyurl.dto.UrlResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ShortenUrlNotFoundException.class)
    public ResponseEntity<UrlResponseErrorDto> handleShortenUrlNotFoundException(ShortenUrlNotFoundException e){
        UrlResponseErrorDto errorDto = UrlResponseErrorDto.of(e.getCode(), e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }
}
