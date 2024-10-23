package com.sparta.newspeed.like.boardLike.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardLikeDefaultResponseDto {

    private String httpStatusCode;
    private String message;
    private BoardLikeResponseDto data;

    public BoardLikeDefaultResponseDto(String httpStatusCode, String message, Long likeCount) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = new BoardLikeResponseDto(likeCount);
    }
}
