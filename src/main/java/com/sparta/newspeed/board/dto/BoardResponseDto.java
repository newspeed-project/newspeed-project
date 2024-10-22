package com.sparta.newspeed.board.dto;

import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    private String statusCode;

    private String message;

    private Long id;


    private String title;

    private String content;

    public BoardResponseDto(String statusCode, String message, Board board) {
        this.statusCode = statusCode;
        this.message = message;
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
