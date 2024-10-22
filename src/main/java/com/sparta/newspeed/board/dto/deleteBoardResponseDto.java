package com.sparta.newspeed.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class deleteBoardResponseDto {
    private String httpStatusCode;
    private String message;

    public deleteBoardResponseDto(String httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
