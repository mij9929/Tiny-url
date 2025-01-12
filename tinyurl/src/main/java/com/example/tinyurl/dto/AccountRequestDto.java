package com.example.tinyurl.dto;

import lombok.Getter;

@Getter
public class AccountRequestDto {
    private String username;
    private String password;

    public AccountRequestDto() { }

    private AccountRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static AccountRequestDto of(String username, String password){
        return new AccountRequestDto(username, password);
    }
}
