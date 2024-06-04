package com.example.tinyurl.controller.api;

import com.example.tinyurl.dto.AccountRequestDto;
import com.example.tinyurl.dto.AccountResponseDto;
import com.example.tinyurl.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserAccountApiController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountApiController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/register")
    public ResponseEntity<AccountResponseDto> register(@RequestBody AccountRequestDto request) {
        AccountResponseDto response = userAccountService.register(request);
        return ResponseEntity.ok(response);
    }

        // 로그인 엔드포인트를 추가할 수 있습니다
}
