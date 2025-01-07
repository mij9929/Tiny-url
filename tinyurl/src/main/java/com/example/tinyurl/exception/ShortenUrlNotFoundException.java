package com.example.tinyurl.exception;

import lombok.Getter;

@Getter
public class ShortenUrlNotFoundException extends RuntimeException{
    private final String code;

    public ShortenUrlNotFoundException(String message, String code){
        super(message);
        this.code = code;
    }

}
