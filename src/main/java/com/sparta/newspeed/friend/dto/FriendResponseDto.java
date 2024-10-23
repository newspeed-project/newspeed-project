package com.sparta.newspeed.friend.dto;

import com.sparta.newspeed.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendResponseDto {
    private Long id;
    private String username;
    private String email;

    public FriendResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

}
