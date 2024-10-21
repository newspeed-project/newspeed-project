package com.sparta.newspeed.domain.user.dto;

import com.sparta.newspeed.domain.user.User;

public class UserResponseDTO {

    private String username;
    private String email;
    private String token;

    public UserResponseDTO(User user, String token) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.token = token;
    }
}
