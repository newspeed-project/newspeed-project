package com.sparta.newspeed.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteBoardRequestDto {
    @NotNull(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
}
