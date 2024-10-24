package com.sparta.newspeed.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotNull(message = "아이디를 입력해 주십시오.")
    private String username;

    @NotNull(message = "비밀번호를 입력해 주십시오.")
    private String password;
}
