package com.example.tinyurl.controller;

import com.example.tinyurl.dto.UrlResponseErrorDto;
import com.example.tinyurl.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class HomeController {
    private final UrlService urlService;

    @Autowired
    public HomeController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortenUrl}")
    public ResponseEntity<?> redirectUrl(HttpServletResponse response, @PathVariable(value = "shortenUrl") String shortenUrl) throws IOException {
        String originUrl = urlService.getOriginUrl(shortenUrl);
        if(originUrl != null) {
            response.sendRedirect(originUrl);
            return null;
        }
        UrlResponseErrorDto urlResponseErrorDto = UrlResponseErrorDto.of("404", "This url is not database");
        return ResponseEntity.ok(urlResponseErrorDto);
    }


}
