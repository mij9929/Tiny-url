package com.example.tinyurl.dto;

import lombok.Getter;

@Getter
public class UrlResponseErrorDto {
    private String status;
    private String message;

    protected UrlResponseErrorDto(){
    }

    private UrlResponseErrorDto(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public static UrlResponseErrorDto of(String status, String message) {
        return new UrlResponseErrorDto(status, message);
    }
}
