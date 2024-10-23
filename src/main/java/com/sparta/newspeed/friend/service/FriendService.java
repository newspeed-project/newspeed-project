package com.sparta.newspeed.friend.service;

import com.sparta.newspeed.domain.friend.Friend;
import com.sparta.newspeed.domain.friend.FriendRepository;
import com.sparta.newspeed.domain.friend.RequestStatus;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.domain.user.UserRepository;
import com.sparta.newspeed.friend.dto.FriendListResponseDto;
import com.sparta.newspeed.friend.dto.FriendRequestDto;
import com.sparta.newspeed.friend.dto.FriendDefaultResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public FriendDefaultResponseDto sendRequest(FriendRequestDto reqDto, User jwtUser) {
        User targetUser = findRequestUser(reqDto.getFriendId());

        Friend friend = new Friend();
        friend.makeFriend(jwtUser, targetUser);
        friendRepository.save(friend);

        return new FriendDefaultResponseDto("201", "친구 요청 성공");
    }

    public FriendListResponseDto getFriendList(User jwtUser) {
        List<Long> friendIds = friendRepository.getFriendIds(jwtUser.getId(), RequestStatus.ACCEPTED);

        List<User> friends = friendIds.stream()
                .map(friendId -> userRepository.findById(friendId)
                        .orElseThrow(() -> new IllegalArgumentException("친구 ID가 잘못되었습니다.")))
                .toList();

        return new FriendListResponseDto("200", "친구 목록 조회 완료", friends);
    }

    public FriendDefaultResponseDto acceptRequest(Long id, User jwtUser) {
        User requestUser = findRequestUser(id);
        Friend friend = findFriend(requestUser, jwtUser);
        friend.accept();

        return new FriendDefaultResponseDto("200", "친구 요청 승인");
    }

    public void deleteFriend(Long id, User jwtUser) {
        User requestUser = findRequestUser(id);
        Friend friend = findFriend(requestUser, jwtUser);
        friendRepository.delete(friend);
    }

    // ========== 편의 메서드 ==========
    private User findRequestUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );
    }

    private Friend findFriend(User requestUser, User responseUser) {
        return friendRepository.findByRequestUserAndResponseUser(requestUser, responseUser).orElseThrow(
                () -> new IllegalArgumentException("친구 관계가 아닙니다.")
        );
    }
}
