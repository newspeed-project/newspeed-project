package com.sparta.newspeed.board.dto;

import com.sparta.newspeed.domain.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadBoardResponseDto {

    private String httpStatusCode;
    private String message;
    private BoardResponseDto data;

    public ReadBoardResponseDto(String httpStatusCode, String message, Board board) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = new BoardResponseDto(board);
    }
}
