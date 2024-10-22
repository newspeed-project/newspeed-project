package com.sparta.newspeed.domain.user.service;

import com.sparta.newspeed.common.JwtUtil;
import com.sparta.newspeed.common.PasswordEncoder;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.domain.user.UserRepository;
import com.sparta.newspeed.domain.user.dto.UserRequestDTO;
import com.sparta.newspeed.domain.user.dto.UserResponseDTO;
import com.sparta.newspeed.domain.user.UserRole;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    //회원 가입

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userRequestDTO.getPassword());

        // 유저 생성 및 필드 설정
        User user = new User();
        user.signup(userRequestDTO.getEmail(), encodedPassword, userRequestDTO.getUsername(), UserRole.USER);
//        user.setEmail(userRequestDTO.getEmail()); // Email 설정
//        user.setPassword(encodedPassword); // 암호화된 비밀번호 설정
//        user.setUsername(userRequestDTO.getUsername()); // 사용자명 설정
//        user.setRole(UserRole.USER); // 기본 역할 설정

        // 유저 저장
        userRepository.save(user);

        // JWT 토큰 발급
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());

        // UserResponseDTO에 유저 정보와 토큰 포함하여 반환
        return new UserResponseDTO(user, token);
    }

}
