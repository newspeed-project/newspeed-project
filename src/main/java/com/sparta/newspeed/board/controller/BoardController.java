package com.sparta.newspeed.board.controller;

import com.sparta.newspeed.board.dto.CreateBoardRequestDto;
import com.sparta.newspeed.board.dto.CreateBoardResponseDto;
import com.sparta.newspeed.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * “title” : “게시글 제목”,
     * “content” : “게시글”
     *
     * “httpStatusCode” : 201,
     * “message” : ,
     * “data” : {
     *   “id”: 1
     * }
     */
    @PostMapping("/board")
    public ResponseEntity<CreateBoardResponseDto> createBoard (@RequestBody @Valid CreateBoardRequestDto reqDto) {
        CreateBoardResponseDto restDto = boardService.createBoard(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(restDto);
    }
}
