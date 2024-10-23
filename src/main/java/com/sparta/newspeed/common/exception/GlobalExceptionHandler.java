package com.sparta.newspeed.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    /* 요청한 자원이 DB에 존재하지 않을 때 예외처리 (404)
    - 해당하는 유저가 없습니다.
    - 해당 댓글이 존재하지 않습니다.
    - 친구 요청 대기 목록이 없습니다.
    - 해당 ID의 게시물을 찾을 수 없습니다.
    - 존재하지 않는 친구 ID입니다.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


    /* 비밀번호 관련
    - 현재 비밀번호와는 동일한 비밀번호로 변경할 수 없습니다. PasswordMismatchException(403)
    - 비밀번호가 일치하지 않습니다. PasswordSameException(401)
     */
    @ExceptionHandler(PasswordSameException.class)
    public ResponseEntity<String> handleGeneralException(PasswordSameException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> handleGeneralException(PasswordMismatchException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }


    /* 클라이언트의 잘못된 요청 (400)
    - 탈퇴한 유저입니다.
    - 중복된 아이디입니다.
    - 자신이 작성한 게시물만 수정 가능합니다.
    - 댓글 작성자 또는 게시글 작성자가 아닌 경우 댓글 수정을 할 수 없습니다.
    - 친구 요청 중이 아니라면 거절 or 승인을 할 수 없습니다.
     */
    @ExceptionHandler(ClientRequestException.class)
    public ResponseEntity<String> handleGeneralException(ClientRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


    /* 토큰관련 인증 문제 (401)
    토큰을 찾을 수 없습니다.
    만료된 토큰입니다.
    잘못된 토큰입니다.(변조 or 파괴 or 지원되지 않는)
     */
    @ExceptionHandler(TokenUnauthorizedException.class)
    public ResponseEntity<String> handleGeneralException(TokenUnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    /* 토큰 관련 인가 문제 403
    TokenAuthorizationException
    필요하다면 추가 예정
     */
}
