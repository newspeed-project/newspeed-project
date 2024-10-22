package com.sparta.newspeed.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateBoardRequestDto {

    @NotNull
    private String title;

    @NotNull
    private String content;
}
