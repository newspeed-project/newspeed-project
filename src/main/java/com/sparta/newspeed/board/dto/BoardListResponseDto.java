package com.sparta.newspeed.board.dto;

import com.sparta.newspeed.domain.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardListResponseDto {

    private String httpStatusCode;
    private String message;
    private List<BoardResponseDto> data;

    public BoardListResponseDto(String httpStatusCode, String message, List<Board> boards) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = boards.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
    }
}
