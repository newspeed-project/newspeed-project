package com.sparta.newspeed.user.service;

import com.sparta.newspeed.common.JwtUtil;
import com.sparta.newspeed.common.PasswordEncoder;
import com.sparta.newspeed.common.exception.ClientRequestException;
import com.sparta.newspeed.common.exception.PasswordMismatchException;
import com.sparta.newspeed.common.exception.PasswordSameException;
import com.sparta.newspeed.common.exception.ResourceNotFoundException;
import com.sparta.newspeed.domain.board.BoardRepository;
import com.sparta.newspeed.domain.comment.CommentRepository;
import com.sparta.newspeed.domain.friend.Friend;
import com.sparta.newspeed.domain.friend.FriendRepository;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.domain.user.UserRepository;
import com.sparta.newspeed.domain.user.UserRole;
import com.sparta.newspeed.user.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final BoardRepository boardRepository;
    private final FriendRepository friendRepository;
    private final CommentRepository commentRepository;

    public UserOneResponseDto createUser(UserRequestDto userRequestDto, HttpServletResponse res) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());

        // 중복 확인
        checkIfPreviousUserExists(userRequestDto.getUsername());

        // 유저 생성 및 필드 설정
        User user = new User();
        user.signup(userRequestDto.getEmail(), encodedPassword, userRequestDto.getUsername(), UserRole.USER);

        // 유저 저장
        userRepository.save(user);

        // JWT 토큰 발급
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());

        jwtUtil.addJwtToCookie(token, res);

        // userRequestDto에 유저 정보를 포함해서 반환
        return new UserOneResponseDto("201", "회원 가입 성공", user);
    }

    public LoginResponseDto login(@Valid LoginRequestDto reqDto, HttpServletResponse res) {
        User user = userRepository.findByUsername(reqDto.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("해당하는 유저가 없습니다.")
        );

        if (user.isActive() == false) {
            throw new ClientRequestException("탈퇴한 유저입니다.");
        }

        checkIfPasswordMatches(user, reqDto.getPassword());

        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);
        return new LoginResponseDto("200", "로그인 성공");
    }

    @Transactional(readOnly = true)
    public UserListResponseDto getAllUsers() {
        List<User> users = userRepository.findAllByActiveTrue();
        return new UserListResponseDto("200", "전체 유저 조회 성공", users);
    }

    public UserOneResponseDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("해당하는 유저가 없습니다.")
        );

        if (user.isActive() == false) {
            throw new ClientRequestException("탈퇴한 유저입니다.");
        }
        return new UserOneResponseDto("200", "특정 유저 조회 성공", user);
    }

    @Transactional
    public UserOneResponseDto updateUser(ProfileUpdateDto reqDto, User jwtUser) {
        checkIfPasswordMatches(jwtUser, reqDto.getPassword());
        checkIfPasswordSameAsBefore(jwtUser, reqDto.getNewPassword());
        reqDto.initPassword(passwordEncoder.encode(reqDto.getNewPassword()));
        jwtUser.update(reqDto.getNewPassword(), reqDto.getEmail());
        userRepository.save(jwtUser);
        return new UserOneResponseDto("200", "유저 정보 수정 성공", jwtUser);
    }

    @Transactional
    public void deleteUser(DeleteRequestDto reqDto, User jwtUser) {
        //1. 비밀번호 검증
        checkIfPasswordMatches(jwtUser, reqDto.getPassword());
        handleUserDeletion(jwtUser);  // 삭제 로직 호출

        //2. 유저 상태를 DELETED 상태로 수정
        jwtUser.delete();      // 유저 Entity 삭제 관련 필드만 수정. 나머지 모든 데이터는 유지
        userRepository.save(jwtUser);

        //3. 친구 관계의 상태 변경 (DELETED)
        List<Friend> friends = friendRepository.findByRequestUserOrResponseUser(jwtUser, jwtUser);
        friends.forEach(Friend::markAsDeleted);
        friendRepository.saveAll(friends);

        //4. 게시글과 댓글 삭제
        boardRepository.deleteByUser(jwtUser);
        commentRepository.deleteByUser(jwtUser);
    }

    @Transactional
    public void handleUserDeletion(User user) {
        // 해당 유저가 연관된 친구 관계를 모두 조회
        List<Friend> friends = friendRepository.findByRequestUserOrResponseUser(user, user);
        friends.forEach(Friend::markAsDeleted); // 상태를 DELETED로 변경
        friendRepository.saveAll(friends);

        // 게시글과 댓글 삭제
        boardRepository.deleteByUser(user);
        commentRepository.deleteByUser(user);

        // 유저 삭제
        userRepository.delete(user);
    }


    // ========== 편의 메서드 ==========

    private void checkIfPreviousUserExists(String username) {
        Long countPreviousUser = userRepository.countAllByUsername(username);
        if (countPreviousUser != 0) {
            throw new ClientRequestException("중복된 아이디입니다.");
        }
    }

    private void checkIfPasswordMatches(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void checkIfPasswordSameAsBefore(User user, String newPassword) {
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new PasswordSameException("현재 비밀번호와는 동일한 비밀번호로 변경할 수 없습니다.");
        }
    }
}
