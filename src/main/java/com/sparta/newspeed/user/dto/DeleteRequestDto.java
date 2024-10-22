package com.sparta.newspeed.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteRequestDto {
    @NotBlank(message = "비밀번호를 알맞게 입력해 주세요.")
    private String password;
}
