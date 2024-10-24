package com.sparta.newspeed.friend.dto;

import com.sparta.newspeed.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FriendListResponseDto {
    private String httpStatusCode;
    private String message;
    private List<FriendResponseDto> data;

    public FriendListResponseDto(String httpStatusCode, String message, List<User> users) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = users.stream()
                .map(FriendResponseDto::new)
                .toList();
    }
}
