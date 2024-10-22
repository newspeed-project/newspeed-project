package com.sparta.newspeed.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    @NotBlank(message = "댓글 내용은 비어있을 수 없습니다.")
    private String content;

}
