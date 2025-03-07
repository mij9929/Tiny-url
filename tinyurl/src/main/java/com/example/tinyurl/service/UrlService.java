package com.example.tinyurl.service;

import com.example.tinyurl.domain.ShortenUrl;
import com.example.tinyurl.dto.UrlRequestDto;
import com.example.tinyurl.dto.ShortenUrlInfoDto;
import com.example.tinyurl.exception.ShortenUrlNotFoundException;
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

    @Transactional(readOnly = true) // db에서 shorten url 조회
    private ShortenUrl getShortenUrlEntity(String shortenUrl){
        long id = base62.decode(shortenUrl);
        return urlRepository.findById(id).orElseThrow(() -> new ShortenUrlNotFoundException("This URL Not Found", "404"));
    }

    @Transactional // 조회환 shorten url dto 변환
    public ShortenUrlInfoDto getShortenUrDto(String shortenUrl){
        ShortenUrl url = getShortenUrlEntity(shortenUrl);
        return ShortenUrlInfoDto.of(url.getCreateAt(), url.getOriginUrl(), shortenUrl, url.getHit());
    }

    @Transactional
    public String generateShortenUrl(UrlRequestDto urlRequestDto) {
        String originUrl = urlRequestDto.getUrl();

        if (!urlUtil.isValidUrl(originUrl)) {
            log.info("Original URL is not valid");
            throw new IllegalArgumentException("Invalid URL format.");
        }

        String normalizedUrl = urlUtil.normalizeUrl(originUrl);
        Optional<ShortenUrl> existingUrl = Optional.ofNullable(urlRepository.findByOriginUrl(normalizedUrl));

        if (existingUrl.isPresent()) {
            return base62.encode(existingUrl.get().getId());
        }

        ShortenUrl save = urlRepository.save(ShortenUrl.of(normalizedUrl));
        return base62.encode(save.getId());
    }


    @Transactional(readOnly = true)
    public String getOriginUrl(String shortenUrl){
        ShortenUrl url = getShortenUrlEntity(shortenUrl);
        return urlUtil.fullURL(url.getOriginUrl());
    }

    @Transactional
    public void deleteShortenUrl(String shortenUrl) {
        ShortenUrl url = getShortenUrlEntity(shortenUrl);
        urlRepository.delete(url);
    }

    @Transactional
    public void increaseShortenUrlHit(String shortenUrl, String referer){
        ShortenUrl url = getShortenUrlEntity(shortenUrl);
        url.increaseHit();
        urlRepository.save(url);

    }
}
