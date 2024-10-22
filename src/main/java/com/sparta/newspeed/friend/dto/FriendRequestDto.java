package com.sparta.newspeed.friend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FriendRequestDto {
    @NotBlank
    private Long friendId;
}
