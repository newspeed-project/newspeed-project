package com.sparta.newspeed.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteBoardRequestDto {
    @NotNull
    private String password;
}
