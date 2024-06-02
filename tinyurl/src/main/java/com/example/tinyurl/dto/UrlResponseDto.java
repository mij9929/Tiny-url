package com.example.tinyurl.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UrlResponseDto {
    String originUrl;
    String shortUrl;
    Long hit;
    LocalDateTime createAt;

    private UrlResponseDto(String originUrl, String shortUrl) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
    }

    private UrlResponseDto(String originUrl, String shortUrl, Long hit) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.hit = hit;;
    }

    private UrlResponseDto(String originUrl, String shortUrl, Long hit, LocalDateTime createAt) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.hit = hit;
        this.createAt = createAt;
    }

    public static UrlResponseDto of(String originUrl, String shortUrl) {
        return new UrlResponseDto(originUrl, shortUrl);
    }

    public static UrlResponseDto of(String originUrl, String shortUrl, Long hit, LocalDateTime createAt) {
        return new UrlResponseDto(originUrl, shortUrl, hit, createAt);
    }


}
