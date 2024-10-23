package com.sparta.newspeed.user.dto;

import com.sparta.newspeed.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserOneResponseDto {

    private String httpStatusCode;
    private String message;
    private UserResponseDto data;

    public UserOneResponseDto(String httpStatusCode, String message, User user) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = new UserResponseDto(user);
    }
}
