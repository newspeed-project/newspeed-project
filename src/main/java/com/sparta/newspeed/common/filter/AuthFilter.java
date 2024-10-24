package com.sparta.newspeed.common.filter;

import com.sparta.newspeed.common.JwtUtil;
import com.sparta.newspeed.common.exception.TokenUnauthorizedException;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.domain.user.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j(topic = "AuthFilter")
@Component
@RequiredArgsConstructor
@Order(2)
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(url) &&
                (url.startsWith("/api/auth"))
        ) {
            // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
            chain.doFilter(request, response); // 다음 Filter 로 이동
        } else {
            // 나머지 API 요청은 인증 처리 진행
            // 토큰 확인
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 substring
                String token = jwtUtil.substringToken(tokenValue);

                // 토큰 검증

                if (!jwtUtil.validateToken(token)) {
                    throw new TokenUnauthorizedException("잘못된 토큰입니다.");
                }

                // 토큰에서 사용자 정보 가져오기
                Claims info = jwtUtil.getUserInfoFromToken(token);

                User user = userRepository.findByUsername(info.getSubject()).orElseThrow(() ->
                        new TokenUnauthorizedException("토큰에 해당하는 유저가 없습니다.")
                );

                request.setAttribute("user", user);
                chain.doFilter(request, response); // 다음 Filter 로 이동
            } else {
                throw new TokenUnauthorizedException("토큰을 찾을 수 없습니다.");
            }
        }
    }

}