package com.example.tinyurl.controller;

import com.example.tinyurl.dto.UrlResponseErrorDto;
import com.example.tinyurl.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Enumeration;
import java.util.Map;


@Slf4j
@Controller
public class HomeController {
    private final UrlService urlService;

    @Autowired
    public HomeController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/url")
    public String home(){
        return "index.html";
    }

    @GetMapping("/url/{shortenUrl}")
    public RedirectView redirectUrl( @RequestHeader(value="referer", required = false) String refererHeader, HttpServletResponse response, @PathVariable(value = "shortenUrl") String shortenUrl,
                                     @RequestHeader(value = "Purpose", required = false) String purpose ){

        // 프리패치인 경우 ( 크롬 브라우저의 경우 프리패치의 기능이 있기 때문에,
        // 프리패치 기능이 활성화 되어있는 경우, 백그라운드에서 미리 렌더링과 동시에
        if(purpose != null && purpose.equals("prefetch")){
            return null;
        }

        String originUrl = urlService.getOriginUrl(shortenUrl);
        RedirectView redirectView = new RedirectView();

        if (originUrl != null) {
            // DB에 존재하는 Shorten URL 이라면 조회수 증가 및 리디렉션
            String referer = refererHeader != null ? refererHeader : "direct";
            urlService.increaseShortenUrlHit(shortenUrl, referer);
            System.out.println("Referer: " + referer);
            redirectView.setUrl(originUrl); // 원본 URL로 리디렉션
            return redirectView;
        }

        // URL이 존재하지 않을 때 에러 페이지로 리디렉션
        log.info("[테스트 로그] - 해당 URL이 존재하지 않습니다: {}", shortenUrl);
        redirectView.setUrl("/error-page"); // 에러 페이지로 리디렉션 설정
        return redirectView;
    }

    @GetMapping("/error-page")
    public ResponseEntity<?> errorPage() {
        UrlResponseErrorDto urlResponseErrorDto = UrlResponseErrorDto.of("404", "This URL is not in the database");
        return ResponseEntity.status(404).body(urlResponseErrorDto);
    }





}