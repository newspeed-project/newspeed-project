package com.sparta.newspeed.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeRequestDto {
    private Long targetId; // 댓글 또는 게시물 ID
    private boolean isComment; // 댓글 여부

    // 필드 초기화를 위한 생성자 (필요시 사용)
    public LikeRequestDto(Long targetId, boolean isComment) {
        this.targetId = targetId;
        this.isComment = isComment;
    }
}

