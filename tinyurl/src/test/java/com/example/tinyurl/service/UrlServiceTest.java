package com.example.tinyurl.service;

import com.example.tinyurl.dto.UrlRequestDto;
import com.example.tinyurl.repository.UrlRepository;
import com.example.tinyurl.util.Base62;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("URL 서비스 테스트")
class UrlServiceTest {

    @Mock
    private Base62 base62;

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("URL 단축 생성기 테스트")
    @Test
    public void givenTestData_whenOriginUrl_thenReturnShortenURL() {
        // given
        UrlRequestDto urlRequestDto = new UrlRequestDto();
        urlRequestDto.setUrl("www.naver.com");
        String s = sut.generateShortenUrl(urlRequestDto);
        System.out.println(s);


    }
}
