package com.example.tinyurl.controller.api;

import com.example.tinyurl.dto.AccountRequestDto;
import com.example.tinyurl.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountApiController {

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountApiController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @PostMapping("/register") // 데이터 바뀔 경우가 있기 때문에, 이 부분은 나중에 RequestDTo로 수정
    public String register(@RequestBody AccountRequestDto accountRequestDto) {
//        AccountRequestDto accountRequestDto = AccountRequestDto.of(username, password);
        userAccountService.register(accountRequestDto);
        return "redirect:/";
    }

}
