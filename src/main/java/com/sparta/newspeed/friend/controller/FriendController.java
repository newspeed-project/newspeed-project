package com.sparta.newspeed.friend.controller;

import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.friend.dto.FriendListResponseDto;
import com.sparta.newspeed.friend.dto.FriendRequestListResponseDto;
import com.sparta.newspeed.friend.dto.FriendRequestDto;
import com.sparta.newspeed.friend.dto.FriendDefaultResponseDto;
import com.sparta.newspeed.friend.service.FriendService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("/friend-request")
    public ResponseEntity<FriendDefaultResponseDto> sendRequest(
            @Valid @RequestBody FriendRequestDto reqDto,
            @RequestAttribute("user") User jwtUser
    ) {
        FriendDefaultResponseDto resDto = friendService.sendRequest(reqDto, jwtUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @GetMapping("/friend")
    public ResponseEntity<FriendListResponseDto> getFriendList(@RequestAttribute("user") User jwtUser) {
        FriendListResponseDto resDto = friendService.getFriendList(jwtUser);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }


    @PutMapping("/friend/{id}/friend-request")
    public ResponseEntity<FriendDefaultResponseDto> acceptRequest(
            @PathVariable Long id,
            @RequestAttribute("user") User jwtUser
    ) {
        FriendDefaultResponseDto resDto = friendService.acceptRequest(id, jwtUser);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @DeleteMapping("/friend/{id}/friend-request")
    public ResponseEntity<Void> rejectRequest(
            @PathVariable Long id,
            @RequestAttribute("user") User jwtUser
    ) {
        friendService.rejectRequest(id, jwtUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/friend/{id}")
    public ResponseEntity<Void> deleteFriend(
            @PathVariable Long id,
            @RequestAttribute("user") User jwtUser
    ) {
        friendService.deleteFriend(id, jwtUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/friend-request")
    public ResponseEntity<FriendRequestListResponseDto> getFriendRequestList(@RequestAttribute("user") User jwtUser) {
        FriendRequestListResponseDto friendRequestList = friendService.getFriendRequestList(jwtUser);
        return ResponseEntity.status(HttpStatus.OK).body(friendRequestList);
    }
}
