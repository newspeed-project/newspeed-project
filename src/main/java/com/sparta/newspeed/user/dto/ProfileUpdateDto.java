package com.sparta.newspeed.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ProfileUpdateDto {
    @NotBlank(message = "이전 비밀번호를 알맞게 입력해 주세요.")
    public String password;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    public String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
    message = "숫자, 문자, 특수문자가 1개 이상 사용되어야 함, 길이: 8 ~ 16")
    public String newPassword;

    public void initPassword(String newPassword) {
        this.password = password;
    }
}
