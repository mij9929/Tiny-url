package com.example.tinyurl.controller.api;

import com.example.tinyurl.dto.UrlRequestDto;
import com.example.tinyurl.dto.UrlResponseDto;
import com.example.tinyurl.service.UrlService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ShortenUrlApiController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/{shortenUrl}/info")
    public ResponseEntity<?> getShortenURl(@PathVariable(value = "shortenUrl") String shortenUrl){
        UrlResponseDto urlResponseDto = urlService.getShortenUrlResponseDto(shortenUrl);
        return ResponseEntity.ok(urlResponseDto);
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody @Valid UrlRequestDto urlRequestDto) {
        String originUrl = urlRequestDto.getUrl();
        String shortenUrl= urlService.generateShortenUrl(urlRequestDto);
        log.info("[origin URL] " + originUrl);
        log.info("[encoded URL] " + shortenUrl);
        UrlResponseDto urlResponseDto = urlService.getShortenUrlResponseDto(shortenUrl);
        return ResponseEntity.ok(urlResponseDto);

    }

    @DeleteMapping("/{shortenUrl}")
    public ResponseEntity<Void> deleteShortenUrl(@PathVariable String shortenUrl) {
        urlService.deleteShortenUrl(shortenUrl);
        log.info("[Delete] " + shortenUrl);
        return ResponseEntity.noContent().build();
    }






}
