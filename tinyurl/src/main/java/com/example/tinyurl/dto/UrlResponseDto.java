package com.example.tinyurl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

@ToString
@Getter
public class UrlResponseDto {
    CreatedDate createAt;
    String originUrl;
    String shortUrl;
    Long hit;

    private UrlResponseDto(String originUrl, String shortUrl) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.hit = 0L;
    }

    private UrlResponseDto(String originUrl, String shortUrl, Long hit) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.hit = hit;;
    }

    public static UrlResponseDto of(String originUrl, String shortUrl) {
        return new UrlResponseDto(originUrl, shortUrl);
    }

    public static UrlResponseDto of(String originUrl, String shortUrl, Long hit) {
        return new UrlResponseDto(originUrl, shortUrl, hit);
    }
}
