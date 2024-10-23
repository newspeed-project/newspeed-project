package com.sparta.newspeed.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String content;

    // 작성자 정보가 필요한 경우
    private Long username;

    // 생성 및 수정 시간을 담을 수도 있음
    private String timestamp; // 예: "2024-10-23T12:34:56"

    // 기본 생성자와 생성자 필요 시 추가 가능
    public CommentRequestDto(String content, Long username) {
        this.content = content;
        this.username = username;
    }
}
