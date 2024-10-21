package com.sparta.newspeed.board.service;

import com.sparta.newspeed.board.dto.BoardRequestDto;
import com.sparta.newspeed.board.dto.BoardResponseDto;
import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sparta.newspeed.common.exception.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<BoardResponseDto> getBoardById(Long id) {
        List<Board> boards = boardRepository.findAllById(id);
        return boards.stream()
                .map(BoardResponseDto::new).toList();
    }
}
