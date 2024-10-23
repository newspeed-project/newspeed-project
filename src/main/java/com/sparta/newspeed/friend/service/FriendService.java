package com.sparta.newspeed.friend.service;

import com.sparta.newspeed.common.exception.ClientRequestException;
import com.sparta.newspeed.common.exception.ResourceNotFoundException;
import com.sparta.newspeed.domain.friend.Friend;
import com.sparta.newspeed.domain.friend.FriendRepository;
import com.sparta.newspeed.domain.friend.RequestStatus;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.domain.user.UserRepository;
import com.sparta.newspeed.domain.comment.CommentRepository;
import com.sparta.newspeed.domain.board.BoardRepository;
import com.sparta.newspeed.friend.dto.FriendDefaultResponseDto;
import com.sparta.newspeed.friend.dto.FriendListResponseDto;
import com.sparta.newspeed.friend.dto.FriendRequestDto;
import com.sparta.newspeed.friend.dto.FriendRequestListResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

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
                        .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 친구 ID입니다.")))
                .toList();

        return new FriendListResponseDto("200", "친구 목록 조회 완료", friends);
    }

    @Transactional
    public void handleUserDeletion(User user) {
        // 요청한 사용자와 응답한 사용자 모두를 고려하여 친구 관계를 DELETED 상태로 변경
        List<Friend> requestFriends = friendRepository.findByRequestUser(user);
        List<Friend> responseFriends = friendRepository.findByResponseUser(user);

        // DELETED 상태로 변경
        requestFriends.forEach(Friend::markAsDeleted);
        responseFriends.forEach(Friend::markAsDeleted);

        friendRepository.saveAll(requestFriends);
        friendRepository.saveAll(responseFriends);

        // 게시글과 댓글 삭제
        boardRepository.deleteByUser(user);
        commentRepository.deleteByUser(user);

        // 유저 삭제
        userRepository.delete(user);
    }


    @Transactional
    public FriendDefaultResponseDto acceptRequest(Long id, User jwtUser) {
        User requestUser = findRequestUser(id);
        Friend friend = findFriend(requestUser, jwtUser);
        friend.accept();

        return new FriendDefaultResponseDto("200", "친구 요청 승인");
    }

    public void rejectRequest(Long id, User jwtUser) {
        User requestUser = findRequestUser(id);
        Friend friend = findFriend(requestUser, jwtUser);
        if (friend.getStatus() == RequestStatus.ACCEPTED) {
            throw new ClientRequestException("이미 친구 상태입니다.");
        }
    }

    public void deleteFriend(Long id, User jwtUser) {
        User requestUser = findRequestUser(id);
        Friend friend = findFriendBidirectional(requestUser, jwtUser);
        friendRepository.delete(friend);
    }

    // ========== 편의 메서드 ==========

    private User findRequestUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("해당하는 유저가 없습니다.")
        );
    }

    private Friend findFriend(User requestUser, User responseUser) {
        return friendRepository.findByRequestUserAndResponseUser(requestUser, responseUser).orElseThrow(
                () -> new ResourceNotFoundException("친구 관계가 아닙니다.")
        );
    }

    private Friend findFriendBidirectional(User requestUser, User responseUser) {
        return friendRepository.findByRequestUserAndResponseUser(requestUser, responseUser)
                .or(() -> friendRepository.findByRequestUserAndResponseUser(responseUser, requestUser))
                .orElseThrow(() -> new ResourceNotFoundException("친구 관계가 아닙니다."));
    }

    public FriendRequestListResponseDto getFriendRequestList(User jwtUser) {
        List<Friend> pendingRequests = friendRepository.findByResponseUserAndStatus(jwtUser, RequestStatus.PENDING);
        return Optional.ofNullable(pendingRequests)
                .filter(list -> !list.isEmpty())
                .map(list -> {
                    List<Long> requestUserIds = list.stream()
                            .map(friend -> friend.getRequestUser().getId())
                            .collect(Collectors.toList());

                    List<User> requestUsers = userRepository.findAllById(requestUserIds);

                    return new FriendRequestListResponseDto("200", "친구 요청 대기 목록 조회 완료", requestUsers);
                })
                .orElseThrow(() -> new ResourceNotFoundException("친구 요청 대기 목록이 없습니다."));
    }
}
