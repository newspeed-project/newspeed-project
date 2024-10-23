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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/signup")
    public ResponseEntity<UserOneResponseDto> signup(
            @Valid @RequestBody UserRequestDto userRequestDto,
            HttpServletResponse response
    ) {
        UserOneResponseDto resDto = userService.createUser(userRequestDto, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto reqDto,
            HttpServletResponse response
    ) {
        LoginResponseDto resDto = userService.login(reqDto, response);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @GetMapping("/user")
    public ResponseEntity<UserListResponseDto> getAllUsers() {
        UserListResponseDto resDto = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserOneResponseDto> getUser(@PathVariable Long id) {
        UserOneResponseDto resDto = userService.getUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @PutMapping("/user")
    public ResponseEntity<UserOneResponseDto> updateUser(@Valid @RequestBody ProfileUpdateDto reqDto, @RequestAttribute("user") User jwtUser) {
        UserOneResponseDto resDto = userService.updateUser(reqDto, jwtUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resDto);
    }

    @DeleteMapping("/user")
    public ResponseEntity<Void> deleteUser(@Valid @RequestBody DeleteRequestDto reqDto, @RequestAttribute("user") User jwtUser) {
        userService.deleteUser(reqDto, jwtUser);
        return ResponseEntity.noContent().build(); //상태 코드 204 반환
    }
}
