package com.example.tinyurl.controller;

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
public class MainController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody @Valid UrlRequestDto urlRequestDto) {
        log.info("[origin URL]" + urlRequestDto.getUrl());
        String shortenUrl= urlService.generateShortenUrl(urlRequestDto);
        log.info("[encoded URL]" + shortenUrl);
        return ResponseEntity.ok(shortenUrl);
    }

}
