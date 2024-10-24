package com.sparta.newspeed.like.commentLike.dto;

import com.sparta.newspeed.like.boardLike.dto.BoardLikeResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentLikeDefaultResponseDto {

    private String httpStatusCode;
    private String message;
    private CommentLikeResponseDto data;

    public CommentLikeDefaultResponseDto(String httpStatusCode, String message, Long likeCount) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = new CommentLikeResponseDto(likeCount);
    }
}
