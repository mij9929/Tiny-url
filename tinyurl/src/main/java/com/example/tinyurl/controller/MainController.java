package com.example.tinyurl.controller;

import com.example.tinyurl.dto.UrlRequestDto;
import com.example.tinyurl.dto.UrlResponseDto;
import com.example.tinyurl.dto.UrlResponseErrorDto;
import com.example.tinyurl.service.UrlService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody @Valid UrlRequestDto urlRequestDto) {
        String originUrl = urlRequestDto.getUrl();
        log.info("[origin URL] " + originUrl);
        String shortenUrl= urlService.generateShortenUrl(urlRequestDto);
        log.info("[encoded URL] " + shortenUrl);

        if(shortenUrl != null) {
            return ResponseEntity.ok( UrlResponseDto.of(originUrl, shortenUrl));
        }

        UrlResponseErrorDto urlResponseErrorDto = UrlResponseErrorDto.of("404", "This url invalid URL");
        return ResponseEntity.ok(urlResponseErrorDto);
    }
//
    @GetMapping("/{shortenUrl}/info")
    public ResponseEntity<?> shortenUrlInfo(@PathVariable(value = "shortenUrl") String shortenUrl){
        UrlResponseDto urlResponseDto = urlService.getShortenUrlResponseDto(shortenUrl);
        return ResponseEntity.ok(urlResponseDto);
    }



}
