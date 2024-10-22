package com.sparta.newspeed.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "유저명은 비어있을 수 없습니다.")
    @Size(max = 10, message = "유저명은 최대 10글자까지 가능합니다.")
    private String username;

    @NotBlank(message = "이메일은 비어있을 수 없습니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
            message = "숫자, 문자, 특수문자가 1개 이상 사용되어야 함, 길이: 8 ~ 16")
    private String password;
}
