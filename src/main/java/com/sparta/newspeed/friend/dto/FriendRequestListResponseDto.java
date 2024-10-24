package com.sparta.newspeed.friend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import com.sparta.newspeed.domain.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class FriendRequestListResponseDto {

    private String httpStatusCode;
    private String message;
    private List<FriendResponseDto> data;

    public FriendRequestListResponseDto(String httpStatusCode, String message, List<User> requestUsersDto) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = requestUsersDto.stream()
                .map(FriendResponseDto::new)
                .collect(Collectors.toList());
    }

}
