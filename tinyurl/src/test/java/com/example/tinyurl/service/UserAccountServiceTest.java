package com.example.tinyurl.service;

import com.example.tinyurl.domain.UserAccount;
import com.example.tinyurl.dto.AccountRequestDto;
import com.example.tinyurl.dto.AccountResponseDto;
import com.example.tinyurl.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.NotNull;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {
    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private UserAccountService userAccountService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @DisplayName("로그인")
    @Test
    void register_givenUserRegisterData_whenLogin_thenReturnAccountBuildData() {
        // given
        String rawPassword = "password";
        String encodedPassword = "$2a$10$MockedEncodedPassword";

        Mockito.when(bCryptPasswordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        UserAccount saveUser = UserAccount.builder()
                .username("test")
                .password(encodedPassword)
                .build();

        Mockito.when(userAccountRepository.save(Mockito.any(UserAccount.class))).thenReturn(saveUser);

        AccountRequestDto accountRequestDto = AccountRequestDto.of("test", rawPassword);

        // when
        AccountResponseDto responseDto = userAccountService.register(accountRequestDto);

        // then
        assertNotNull(responseDto);
        assertEquals("test", responseDto.getUsername());

        Mockito.verify(userAccountRepository, Mockito.times(1)).save(Mockito.any(UserAccount.class));

        // Capture: save 메서드에 전달된 인수 캡처
        ArgumentCaptor<UserAccount> captor = ArgumentCaptor.forClass(UserAccount.class);
        Mockito.verify(userAccountRepository).save(captor.capture());

        UserAccount capturedUser = captor.getValue();
        assertEquals("test", capturedUser.getUsername());
        assertEquals(encodedPassword, capturedUser.getPassword()); // 고정된 암호화 값 확인
    }


}