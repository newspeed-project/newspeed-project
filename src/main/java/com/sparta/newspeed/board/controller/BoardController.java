package com.sparta.newspeed.board.controller;

import com.sparta.newspeed.board.dto.*;
import com.sparta.newspeed.board.service.BoardService;
import com.sparta.newspeed.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{id}")
    public ResponseEntity<BoardOneResponseDto> getBoard(@PathVariable Long id) {
        BoardOneResponseDto board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }

    @PostMapping("")
    public ResponseEntity<BoardOneResponseDto> createBoard (@RequestBody @Valid UpdateBoardRequestDto reqDto, @RequestAttribute("user") User jwtUser) {
        BoardOneResponseDto resDto = boardService.createBoard(reqDto, jwtUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @GetMapping
    public ResponseEntity<BoardListResponseDto> getAllBoards(@RequestAttribute("user") User jwtUser) {
        BoardListResponseDto boards = boardService.getAllBoards(jwtUser);
        return ResponseEntity.ok(boards);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditBoardResponseDto> editBoard (@PathVariable Long id, @RequestBody @Valid UpdateBoardRequestDto reqDto, @RequestAttribute("user") User jwtUser) {
        EditBoardResponseDto resDto = boardService.editBoard(id, reqDto, jwtUser);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id, @RequestBody @Valid DeleteBoardRequestDto reqDto, @RequestAttribute("user") User jwtUser) {
        boardService.deleteBoard(id, reqDto, jwtUser);
        return ResponseEntity.noContent().build();
    }
}
