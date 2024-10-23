package com.sparta.newspeed.user.dto;

import com.sparta.newspeed.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserListResponseDto {

    private String httpStatusCode;
    private String message;
    private List<UserResponseDto> data;

    public UserListResponseDto(String httpStatusCode, String message, List<User> users) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = users.stream()
                .map(UserResponseDto::new)
                .toList();
    }
}
