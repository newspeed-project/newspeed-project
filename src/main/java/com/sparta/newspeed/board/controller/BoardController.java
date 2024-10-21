package com.sparta.newspeed.board.controller;

import com.sparta.newspeed.board.dto.CreateBoardRequestDto;
import com.sparta.newspeed.board.dto.CreateBoardResponseDto;
import com.sparta.newspeed.board.dto.BoardRequestDto;
import com.sparta.newspeed.board.dto.BoardResponseDto;
import com.sparta.newspeed.board.service.BoardService;
import com.sparta.newspeed.domain.board.Board;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{id}")
    public ResponseEntity<List<BoardResponseDto>> getBoard(@PathVariable Long id) {
        List<BoardResponseDto> board = boardService.getBoardById(id);
        if(board.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(board);
    }



    @PostMapping("/")
    public ResponseEntity<CreateBoardResponseDto> createBoard (@RequestBody @Valid CreateBoardRequestDto reqDto) {
        CreateBoardResponseDto restDto = boardService.createBoard(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(restDto);
    }
}
