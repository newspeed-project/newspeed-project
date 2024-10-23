package com.sparta.newspeed.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeResponseDto {
    private Long id;  // 댓글 ID
    private int likeCount;  // 좋아요 수
    private boolean liked;  // 좋아요 여부

    public LikeResponseDto(Long id, int likeCount, boolean liked) {
        this.id = id;
        this.likeCount = likeCount;
        this.liked = liked;
    }


}
