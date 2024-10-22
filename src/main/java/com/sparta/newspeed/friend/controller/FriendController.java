package com.sparta.newspeed.friend.controller;

import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.friend.dto.FriendRequestDto;
import com.sparta.newspeed.friend.service.FriendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/friend-request")
    public ResponseEntity<Void> sendRequest(
            @Valid @RequestBody FriendRequestDto reqDto,
            @RequestAttribute("user") User jwtUser
    ) {
        friendService.sendRequest(reqDto, jwtUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
