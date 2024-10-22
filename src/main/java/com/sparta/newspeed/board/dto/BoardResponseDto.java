package com.sparta.newspeed.board.dto;

import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponseDto {

    // private User user;

    private Long id;

    @NotBlank
    private String title;

    private String content;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
