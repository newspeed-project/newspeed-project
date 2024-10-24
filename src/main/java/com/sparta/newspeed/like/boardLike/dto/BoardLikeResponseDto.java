package com.sparta.newspeed.like.boardLike.dto;

import lombok.Getter;

@Getter
public class BoardLikeResponseDto {
    private Long likeCount;

    public BoardLikeResponseDto(Long count) {
        this.likeCount = count;
    }
}
