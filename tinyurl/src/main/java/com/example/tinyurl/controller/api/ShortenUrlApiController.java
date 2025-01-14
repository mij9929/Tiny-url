package com.example.tinyurl.controller.api;

import com.example.tinyurl.dto.UrlRequestDto;
import com.example.tinyurl.dto.UrlResponseDto;
import com.example.tinyurl.dto.UrlResponseErrorDto;
import com.example.tinyurl.service.UrlService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ShortenUrlApiController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody @Valid UrlRequestDto urlRequestDto) {
        String originUrl = urlRequestDto.getUrl();
        log.info("[origin URL] " + originUrl);
        try {
            // 단축 URL 생성
            String shortenUrl = urlService.generateShortenUrl(urlRequestDto);
            log.info("[encoded URL] " + shortenUrl);

            if (shortenUrl != null) {
                // 성공적으로 생성된 경우 응답 반환
                return ResponseEntity.ok(UrlResponseDto.of(originUrl, shortenUrl));
            } else {
                // URL 유효성 검사 실패 시 에러 응답 반환
                UrlResponseErrorDto urlResponseErrorDto = UrlResponseErrorDto.of("400", "Invalid URL format.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(urlResponseErrorDto);
            }
        } catch (Exception e) {
            // 예외 발생 시 에러 메시지 반환
            log.error("Error processing the URL", e);
            UrlResponseErrorDto errorDto = UrlResponseErrorDto.of("500", "An internal server error occurred.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
        }
    }

//
    @GetMapping("/{shortenUrl}/info")
    public ResponseEntity<?> shortenUrlInfo(@PathVariable(value = "shortenUrl") String shortenUrl){
        UrlResponseDto urlResponseDto = urlService.getShortenUrlResponseDto(shortenUrl);
        return ResponseEntity.ok(urlResponseDto);
    }



}
