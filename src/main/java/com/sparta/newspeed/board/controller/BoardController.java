package com.sparta.newspeed.board.controller;

import com.sparta.newspeed.board.dto.*;
import com.sparta.newspeed.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable Long id) {
        BoardResponseDto board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }

    @PostMapping("")
    public ResponseEntity<CreateBoardResponseDto> createBoard (@RequestBody @Valid UpdateBoardRequestDto reqDto) {
        CreateBoardResponseDto resDto = boardService.createBoard(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditBoardResponseDto> editBoard (@PathVariable Long id, @RequestBody @Valid UpdateBoardRequestDto reqDto) {
        EditBoardResponseDto resDto = boardService.editBoard(id, reqDto);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
        List<BoardResponseDto> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id, @RequestBody @Valid DeleteBoardRequestDto reqDto) {
        boardService.deleteBoard(id, reqDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
