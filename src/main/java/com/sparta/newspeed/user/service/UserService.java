package com.sparta.newspeed.user.service;

import com.sparta.newspeed.common.PasswordEncoder;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.domain.user.UserRepository;
import com.sparta.newspeed.user.dto.ProfileUpdateDto;
import com.sparta.newspeed.user.dto.UserResponseDto;
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
    public UserResponseDto updateUser(Long id, ProfileUpdateDto reqDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );

        checkIfPasswordMatches(user, reqDto.getPassword());
        checkIfPasswordSameAsBefore(user, reqDto.getNewPassword());

        reqDto.initPassword(passwordEncoder.encode(reqDto.getNewPassword()));

        user.update(reqDto.getNewPassword(), reqDto.getUsername());
        return new UserResponseDto(user);
    }

    // ========== 편의 메서드 ==========
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
