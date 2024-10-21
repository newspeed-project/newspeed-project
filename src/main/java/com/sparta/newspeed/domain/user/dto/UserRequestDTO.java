package com.sparta.newspeed.domain.user.dto;

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
public class UserRequestDTO {

    @NotBlank(message = "유저명은 비어있을 수 없습니다.")
    @Size(max = 10, message = "유저명은 최대 10글자까지 가능합니다.")
    private String username;
    @NotBlank(message = "이메일은 비어있을 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    private String password;
}
