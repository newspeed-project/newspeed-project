package com.sparta.newspeed.board.service;

import com.sparta.newspeed.board.dto.*;
import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.board.BoardRepository;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.common.exception.ResourceNotFoundException;
import com.sparta.newspeed.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateBoardResponseDto createBoard(UpdateBoardRequestDto reqDto) {
        // 임시 가짜 유저 엔티티 생성 쿠키 토큰 생기면 만들어야함.
        User user = userRepository.findById(1L).orElseThrow(IllegalArgumentException::new);

        Board board = new Board(
                user,
                reqDto.getTitle(),
                reqDto.getContent()
        );

        boardRepository.save(board);

        return new CreateBoardResponseDto("201", "게시물 생성 완료", board.getId());
    }

    public ReadBoardResponseDto getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
        return new ReadBoardResponseDto("200", "특정 게시물 조회 완료", board);
    }

    public ReadAllBoardResponseDto getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return new ReadAllBoardResponseDto("200", "전체 게시물 조회 완료", boards);
    }

    @Transactional
    public EditBoardResponseDto editBoard(Long id, UpdateBoardRequestDto reqDto) {

        Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        board.update(
                reqDto.getTitle(),
                reqDto.getContent()
        );

        return new EditBoardResponseDto("200", "게시물 수정 완료", board);
    }
}
