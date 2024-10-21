package com.sparta.newspeed.board.controller;

import com.sparta.newspeed.board.dto.BoardRequestDto;
import com.sparta.newspeed.board.dto.BoardResponseDto;
import com.sparta.newspeed.board.service.BoardService;
import com.sparta.newspeed.domain.board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
