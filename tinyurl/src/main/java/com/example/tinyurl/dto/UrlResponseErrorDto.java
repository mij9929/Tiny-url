package com.example.tinyurl.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UrlResponseErrorDto {
    private String status;
    private String msg;

    protected UrlResponseErrorDto(){
    }

    private UrlResponseErrorDto(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static UrlResponseErrorDto of(String status, String msg) {
        return new UrlResponseErrorDto(status, msg);
    }
}
