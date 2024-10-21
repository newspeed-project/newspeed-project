package com.sparta.newspeed.board.dto;

import lombok.Getter;

@Getter
public class CreateBoardResponseDto {
    private String statusCode;
    private String message;
    private Long id;
    public CreateBoardResponseDto(String statusCode, String message, Long id) {
        this.statusCode = statusCode;
        this.message = message;
        this.id = id;
    }
}
