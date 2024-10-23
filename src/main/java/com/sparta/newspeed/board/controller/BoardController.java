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
    public ResponseEntity<ReadBoardResponseDto> getBoard(@PathVariable Long id) {
        ReadBoardResponseDto board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }

    @PostMapping("")
    public ResponseEntity<CreateBoardResponseDto> createBoard (@RequestBody @Valid UpdateBoardRequestDto reqDto, @RequestAttribute("user") User jwtUser) {
        CreateBoardResponseDto resDto = boardService.createBoard(reqDto, jwtUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditBoardResponseDto> editBoard (@PathVariable Long id, @RequestBody @Valid UpdateBoardRequestDto reqDto, @RequestAttribute("user") User jwtUser) {
        EditBoardResponseDto resDto = boardService.editBoard(id, reqDto, jwtUser);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @GetMapping
    public ResponseEntity<ReadAllBoardResponseDto> getAllBoards() {
        ReadAllBoardResponseDto boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id, @RequestBody @Valid DeleteBoardRequestDto reqDto, @RequestAttribute("user") User jwtUser) {
        boardService.deleteBoard(id, reqDto, jwtUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
