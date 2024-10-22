package com.sparta.newspeed.user.controller;

import com.sparta.newspeed.user.dto.DeleteRequestDto;
import com.sparta.newspeed.user.dto.ProfileUpdateDto;
import com.sparta.newspeed.user.dto.UserResponseDto;
import com.sparta.newspeed.user.service.UserService;
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

    @PutMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @Valid ProfileUpdateDto reqDto) {
        UserResponseDto user = userService.updateUser(id, reqDto);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @Valid DeleteRequestDto reqDto) {
        userService.deleteUser(id, reqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
