package com.sparta.newspeed.comment.dto;

import com.sparta.newspeed.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentOneResponseDto {
    private String httpStatusCode;
    private String message;
    private CommentResponseDto data;

    public CommentOneResponseDto(String httpStatusCode, String message, Comment comment) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = new CommentResponseDto(comment);
    }
}
