package com.sparta.newspeed.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateBoardRequestDto {

    @NotNull(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotNull(message = "내용은 필수 입력 값입니다.")
    private String content;
}
