package com.sparta.newspeed.comment.dto;

import com.sparta.newspeed.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;

    private String content;

    private String createdAt;
    private String updatedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt().toString();
        this.updatedAt = comment.getModifiedAt().toString();
    }
}
