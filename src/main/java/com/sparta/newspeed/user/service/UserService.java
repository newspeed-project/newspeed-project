package com.sparta.newspeed.user.service;

import com.sparta.newspeed.common.JwtUtil;
import com.sparta.newspeed.common.PasswordEncoder;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.domain.user.UserRepository;
import com.sparta.newspeed.domain.user.UserRole;
import com.sparta.newspeed.user.dto.*;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponseDto createUser(UserRequestDto userRequestDto, HttpServletResponse res) {
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
        return new UserResponseDto(user);
    }

    public UserResponseDto login(@Valid LoginRequestDto reqDto, HttpServletResponse res) {
        User user = userRepository.findByUsername(reqDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );

        if (user.isActive() == false) {
            throw new IllegalArgumentException("탈퇴한 유저입니다.");
        }

        checkIfPasswordMatches(user, reqDto.getPassword());

        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users) {
            userResponseDtos.add(new UserResponseDto(user));
        }
        return userResponseDtos;
    }

    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(ProfileUpdateDto reqDto, User jwtUser) {
        checkIfPasswordMatches(jwtUser, reqDto.getPassword());
        checkIfPasswordSameAsBefore(jwtUser, reqDto.getNewPassword());

        reqDto.initPassword(passwordEncoder.encode(reqDto.getNewPassword()));

        jwtUser.update(reqDto.getNewPassword(), reqDto.getEmail());
        return new UserResponseDto(jwtUser);
    }

    @Transactional
    public void deleteUser(DeleteRequestDto reqDto, User jwtUser) {
        checkIfPasswordMatches(jwtUser, reqDto.getPassword());
        jwtUser.delete();      // 유저 Entity 삭제 관련 필드만 수정. 나머지 모든 데이터는 유지
    }

    // ========== 편의 메서드 ==========

    private void checkIfPreviousUserExists(String username) {
        Long countPreviousUser = userRepository.countAllByUsername(username);
        if (countPreviousUser != 0) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
    }

    private void checkIfPasswordMatches(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void checkIfPasswordSameAsBefore(User user, String newPassword) {
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호와는 동일한 비밀번호로 변경할 수 없습니다.");
        }
    }
}
