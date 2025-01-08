package com.example.tinyurl.service;

import com.example.tinyurl.domain.UserAccount;
import com.example.tinyurl.dto.AccountRequestDto;
import com.example.tinyurl.dto.AccountResponseDto;
import com.example.tinyurl.repository.UserAccountRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserAccountService(UserAccountRepository userAccountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public AccountResponseDto register(AccountRequestDto request) {
        UserAccount user = UserAccount.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
        userAccountRepository.save(user);

        return AccountResponseDto.builder().username(user.getUsername()).build();
    }

    public AccountResponseDto login(AccountRequestDto request) {
        UserAccount user = userAccountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 로그인 성공 후 인증 정보 설정
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return AccountResponseDto.builder().username(user.getUsername()).build();
    }
}
