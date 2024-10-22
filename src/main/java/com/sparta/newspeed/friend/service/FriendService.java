package com.sparta.newspeed.friend.service;

import com.sparta.newspeed.domain.friend.Friend;
import com.sparta.newspeed.domain.friend.FriendRepository;
import com.sparta.newspeed.domain.friend.RequestStatus;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.domain.user.UserRepository;
import com.sparta.newspeed.friend.dto.FriendListResponseDto;
import com.sparta.newspeed.friend.dto.FriendRequestDto;
import com.sparta.newspeed.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public void sendRequest(FriendRequestDto reqDto, User jwtUser) {
        User targetUser = userRepository.findById(reqDto.getFriendId()).orElseThrow(
                () -> new IllegalArgumentException("친구 ID가 잘못되었습니다.")
        );

        Friend friend = new Friend();
        friend.makeFriend(jwtUser, targetUser);
        friendRepository.save(friend);
    }

    public FriendListResponseDto getFriendList(User jwtUser) {
        List<Long> friendIds = friendRepository.getFriendIds(jwtUser.getId(), RequestStatus.ACCEPTED);

        List<User> friends = friendIds.stream()
                .map(friendId -> userRepository.findById(friendId)
                        .orElseThrow(() -> new IllegalArgumentException("친구 ID가 잘못되었습니다.")))
                .toList();

        List<UserResponseDto> resDto = friends.stream()
                .map(UserResponseDto::new)
                .toList();
        return new FriendListResponseDto("200", "친구 목록 조회 완료", resDto);
    }

    public void acceptRequest(Long id, User jwtUser) {
        Friend friend = friendRepository.findByResponseUser(jwtUser);
        friend.accept();
    }

    public void rejectRequest(Long id, User jwtUser) {
        Friend friend = friendRepository.findByResponseUser(jwtUser);
        friendRepository.delete(friend);
    }
}
