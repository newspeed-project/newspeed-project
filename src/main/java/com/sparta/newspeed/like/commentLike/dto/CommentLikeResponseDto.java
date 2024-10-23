package com.sparta.newspeed.like.commentLike.dto;

import lombok.Getter;

@Getter
public class CommentLikeResponseDto {
    private Long likeCount;

    public CommentLikeResponseDto(Long count) {
        this.likeCount = count;
    }
}
