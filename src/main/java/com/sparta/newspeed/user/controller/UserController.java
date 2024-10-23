package com.sparta.newspeed.user.controller;

import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.user.dto.*;
import com.sparta.newspeed.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<UserResponseDto> signup(
            @Valid @RequestBody UserRequestDto userRequestDto,
            HttpServletResponse response
    ) {
        UserResponseDto user = userService.createUser(userRequestDto, response);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserResponseDto> login(
            @Valid @RequestBody LoginRequestDto reqDto,
            HttpServletResponse response
    ) {
        UserResponseDto user = userService.login(reqDto, response);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        UserResponseDto user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody ProfileUpdateDto reqDto, @RequestAttribute("user") User jwtUser) {
        UserResponseDto user = userService.updateUser(reqDto, jwtUser);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(@Valid @RequestBody DeleteRequestDto reqDto, @RequestAttribute("user") User jwtUser) {
        userService.deleteUser(reqDto, jwtUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
