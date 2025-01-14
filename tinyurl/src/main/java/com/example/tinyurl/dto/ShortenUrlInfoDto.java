package com.example.tinyurl.dto;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;


import java.time.LocalDateTime;

@ToString
@Getter
public class ShortenUrlInfoDto {
    LocalDateTime createAt;
    String originUrl;
    String shortUrl;
    Long hit;

    private ShortenUrlInfoDto(LocalDateTime createAt, String originUrl, String shortUrl, Long hit) {
        this.createAt = createAt;
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.hit = hit;
    }
    public static ShortenUrlInfoDto of(LocalDateTime createAt, String originUrl, String shortUrl, Long hit) {
        return new ShortenUrlInfoDto(createAt, originUrl, shortUrl, hit);
    }
}
