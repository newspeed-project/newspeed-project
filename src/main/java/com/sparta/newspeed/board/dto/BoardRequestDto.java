package com.sparta.newspeed.board.dto;

import com.sparta.newspeed.domain.board.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Setter
@NoArgsConstructor
public class BoardRequestDto {

    // private User user;

    private String title;

    private String content;
}
