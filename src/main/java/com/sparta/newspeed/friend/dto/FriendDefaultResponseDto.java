package com.sparta.newspeed.friend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendDefaultResponseDto {

    private String httpStatusCode;
    private String message;

    public FriendDefaultResponseDto(String httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
