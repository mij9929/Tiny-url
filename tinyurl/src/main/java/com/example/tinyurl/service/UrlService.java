package com.example.tinyurl.service;

import com.example.tinyurl.domain.Url;
import com.example.tinyurl.dto.UrlRequestDto;
import com.example.tinyurl.dto.UrlResponseDto;
import com.example.tinyurl.repository.UrlRepository;
import com.example.tinyurl.util.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UrlService {

    // origin url ->
    // hash 값 앞자리 10글자를 인코딩
    private final Base62 base62;
    private final UrlRepository urlRepository;


    @Autowired
    public UrlService(Base62 base62, UrlRepository urlRepository) {
        this.base62 = base62;
        this.urlRepository = urlRepository;
    }

    public String generateShortenUrl(UrlRequestDto urlRequestDto){
        String originUrl = urlRequestDto.getUrl();
        Optional<Url> existingUrl = Optional.ofNullable(urlRepository.findByOriginUrl(originUrl));

        if(existingUrl.isPresent()){
            return base62.encode(existingUrl.get().getId());
        }

        Url save = urlRepository.save(Url.of(originUrl));
        System.out.println(base62.encode(1L));
        return base62.encode(save.getId());
    }


    public String getOriginUrl(String shortenUrl){
        long id = base62.decode(shortenUrl);
        Url  url = urlRepository.findById(id).orElse(null);
        if(url != null){
            url.increaseVisitCount();
            urlRepository.save(url);
            return url.getOriginUrl();
        }
        return null;
    }


}
