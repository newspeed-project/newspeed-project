package com.sparta.newspeed.friend.dto;

import com.sparta.newspeed.domain.friend.Friend;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendResponseDto {
    private Long friendId;

    public FriendResponseDto(Long friendId) {
        this.friendId = friendId;
    }
}
