package com.sparta.newspeed.domain.user.controller;

import com.sparta.newspeed.domain.user.dto.UserRequestDTO;
import com.sparta.newspeed.domain.user.dto.UserResponseDTO;
import com.sparta.newspeed.domain.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //1. 유저 생성 (회원 가입)
    @PostMapping("/auth/signup")
    public ResponseEntity<UserResponseDTO> signup(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO responseDTO = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
