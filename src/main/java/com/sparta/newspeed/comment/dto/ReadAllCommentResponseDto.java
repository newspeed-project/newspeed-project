package com.sparta.newspeed.comment.dto;

import com.sparta.newspeed.domain.comment.Comment;
import com.sparta.newspeed.board.dto.BoardResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ReadAllCommentResponseDto {

    private String httpStatusCode;
    private String message;
    private List<CommentResponseDto> data;

    public ReadAllCommentResponseDto(String httpStatusCode, String message, List<Comment> comment) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = comment.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

}
