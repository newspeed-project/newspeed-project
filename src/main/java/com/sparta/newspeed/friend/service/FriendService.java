package com.sparta.newspeed.friend.service;

import com.sparta.newspeed.domain.friend.Friend;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.domain.user.UserRepository;
import com.sparta.newspeed.friend.dto.FriendRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;

    public void sendRequest(FriendRequestDto reqDto, User jwtUser) {
        User targetUser = userRepository.findById(reqDto.getFriendId()).orElseThrow(
                () -> new IllegalArgumentException("친구 ID가 잘못되었습니다.")
        );

        Friend friend = new Friend();
        friend.makeFriend(jwtUser, targetUser);
    }
}
