package com.example.tinyurl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UrlResponseDto {
    String originUrl;
    String shortUrl;

    private UrlResponseDto(String originUrl, String shortUrl) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
    }

    public static UrlResponseDto of(String originUrl, String shortUrl) {
        return new UrlResponseDto(originUrl, shortUrl);
    }
}
