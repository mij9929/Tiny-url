package com.example.tinyurl.service;

import com.example.tinyurl.domain.ShortenUrl;
import com.example.tinyurl.dto.UrlRequestDto;
import com.example.tinyurl.repository.UrlRepository;
import com.example.tinyurl.util.Base62;
import com.example.tinyurl.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Transactional
@Service
public class UrlService {

    // origin url ->
    // hash 값 앞자리 10글자를 인코딩
    private final Base62 base62;
    private final UrlRepository urlRepository;
    private final UrlUtil urlUtil;

    @Autowired
    public UrlService(Base62 base62, UrlRepository urlRepository, UrlUtil urlUtil) {
        this.base62 = base62;
        this.urlRepository = urlRepository;
        this.urlUtil = urlUtil;
    }


    public String generateShortenUrl(UrlRequestDto urlRequestDto){
        String originUrl = urlRequestDto.getUrl();

        if(!urlUtil.isValidUrl(originUrl)) {
            log.info("original Url is not valid");
            return null;
        }

        String normalizedUrl = urlUtil.normalizeUrl(originUrl);
        Optional<ShortenUrl> existingUrl = Optional.ofNullable(urlRepository.findByOriginUrl(normalizedUrl));

        if(existingUrl.isPresent()){
            return base62.encode(existingUrl.get().getId());
        }

        ShortenUrl save = urlRepository.save(ShortenUrl.of(normalizedUrl));
        return base62.encode(save.getId());
    }


    public String getOriginUrl(String shortenUrl){
        long id = base62.decode(shortenUrl);
        ShortenUrl url = urlRepository.findById(id).orElse(null);

        if(url != null){
            url.increaseVisitCount();
            urlRepository.save(url);
            return urlUtil.fullURL(url.getOriginUrl());
        }
        log.info("get originalUrl is null");
        return null;
    }


}
