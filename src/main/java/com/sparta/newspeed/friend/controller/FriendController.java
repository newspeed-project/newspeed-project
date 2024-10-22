package com.sparta.newspeed.friend.controller;

import com.sparta.newspeed.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Requestmapping("/api")
public class FriendController {

    private final FriendService friendService;

//    @PostMapping("/friend-request")
//    public
}
