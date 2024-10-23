package com.sparta.newspeed.friend.dto;

import com.sparta.newspeed.user.dto.UserResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FriendListResponseDto {
    private String httpStatusCode;
    private String message;
    private List<UserResponseDto> data;

    public FriendListResponseDto(String httpStatusCode, String message, List<UserResponseDto> data) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = data;
    }
}
