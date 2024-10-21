package com.sparta.newspeed.common.filter;

import com.sparta.newspeed.common.JwtUtil;
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

        if (StringUtils.hasText(url) && (url.startsWith("/api/auth"))) {
            // 회원가입, 로그인 관련 API는 인증 필요없이 요청 진행
            chain.doFilter(request, response);
        } else {
            // 나머지 API 요청은 인증 처리 진행

            // 토큰 확인
            String tokenValue = String.valueOf(jwtUtil.getTokenFromRequest(httpServletRequest));

            // 토큰에서 JWT 추출
            if (!StringUtils.hasText(tokenValue)) {
                throw new IllegalArgumentException("Not Found token:");
            }
            String token = jwtUtil.substringToken(tokenValue);

            // 토큰 검증
            if (!jwtUtil.validateToken(token)) {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 사용자 정보 추출
            Claims info = jwtUtil.getUserInfoFromToken(token);

            User user = userRepository.findByUsername(info.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("User Not Found")
            );

            request.setAttribute("user", user);
            chain.doFilter(request, response);
        }
    }
}
