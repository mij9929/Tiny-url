package com.example.tinyurl.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
@Component
public class UrlUtil {
    private final RestTemplate restTemplate;

    public UrlUtil(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String fullURL(String url){
        return String.format("https://%s", url);
    }

    public String normalizeUrl(String originUrl){
        try {
            URL url = new URL(originUrl);
            return url.getHost();
        }
        catch (MalformedURLException e){
            log.error("MalformedURLException" + e);
            return null;
        }
    }

    public boolean isValidUrl(String url){
        try{
            restTemplate.headForHeaders(url);
            log.info("[Corrected URL] " + url );
            return true;
        }catch (HttpClientErrorException | HttpServerErrorException e){
            log.info("[Uncorrected URL]" + url );
            return false;
        }
    }
}
