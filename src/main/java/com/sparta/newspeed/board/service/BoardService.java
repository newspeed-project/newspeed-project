package com.sparta.newspeed.board.service;

import com.sparta.newspeed.board.dto.*;
import com.sparta.newspeed.common.PasswordEncoder;
import com.sparta.newspeed.common.exception.ResourceNotFoundException;
import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.board.BoardRepository;
import com.sparta.newspeed.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateBoardResponseDto createBoard(UpdateBoardRequestDto reqDto, User jwtUser) {
        Board board = Board.createBoard(
                jwtUser,
                reqDto.getTitle(),
                reqDto.getContent()
        );
        boardRepository.save(board);
        return new CreateBoardResponseDto("201", "게시물 생성 완료", board.getId());
    }

    public ReadBoardResponseDto getBoardById(Long id) {
        Board board = findBoardById(id);
        return new ReadBoardResponseDto("200", "특정 게시물 조회 완료", board);
    }

    public ReadAllBoardResponseDto getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return new ReadAllBoardResponseDto("200", "전체 게시물 조회 완료", boards);
    }

    @Transactional
    public EditBoardResponseDto editBoard(Long id, UpdateBoardRequestDto reqDto, User jwtUser) {
        Board board = findBoardById(id);
        board.update(
                reqDto.getTitle(),
                reqDto.getContent()
        );
        checkIfCreator(jwtUser, board);
        return new EditBoardResponseDto("200", "게시물 수정 완료", board);
    }

    @Transactional
    public void deleteBoard(Long id, DeleteBoardRequestDto reqDto, User jwtUser) {
        Board board = findBoardById(id);
        checkIfCreator(jwtUser, board);
        checkUserPassword(reqDto.getPassword(), jwtUser);
        boardRepository.delete(board);
    }

    // ========== 편의 메서드 ==========

    private Board findBoardById(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 ID의 게시물을 찾을 수 없습니다.")
        );
    }

    private void checkUserPassword (String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    private void checkIfCreator(User jwtUser, Board board) {
        if (!board.getUser().getId().equals(jwtUser.getId())) {
            throw new IllegalArgumentException("자신이 작성한 게시물만 수정 가능합니다.");
        }
    }
}
