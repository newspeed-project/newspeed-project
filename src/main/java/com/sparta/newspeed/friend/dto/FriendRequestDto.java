package com.sparta.newspeed.friend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FriendRequestDto {
    @NotNull
    private Long friendId;
}
