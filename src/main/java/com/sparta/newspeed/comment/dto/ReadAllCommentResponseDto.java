package com.sparta.newspeed.comment.dto;

import com.sparta.newspeed.domain.comment.Comment;
import com.sparta.newspeed.board.dto.BoardResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadAllCommentResponseDto {

    private String httpStatusCode;
    private String message;
    private CommentResponseDto data;

    public ReadAllCommentResponseDto(String httpStatusCode, String message, Comment comment) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = new CommentResponseDto(comment);
    }

}
