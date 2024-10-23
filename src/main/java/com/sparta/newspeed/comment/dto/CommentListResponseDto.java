package com.sparta.newspeed.comment.dto;

import com.sparta.newspeed.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.stream.Collectors;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentListResponseDto {

    private String httpStatusCode;
    private String message;
    private List<CommentResponseDto> data;

    public CommentListResponseDto(String httpStatusCode, String message, List<Comment> comment) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = comment.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

}
