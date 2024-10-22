package com.sparta.newspeed.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateBoardResponseDto {

    private String httpStatusCode;
    private String message;
    private Long id;

    public CreateBoardResponseDto(String httpStatusCode, String message, Long id) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.id = id;
    }
}
