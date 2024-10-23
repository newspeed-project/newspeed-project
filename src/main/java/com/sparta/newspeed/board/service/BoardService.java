package com.sparta.newspeed.board.service;

import com.sparta.newspeed.board.dto.*;
import com.sparta.newspeed.common.PasswordEncoder;
import com.sparta.newspeed.common.exception.ClientRequestException;
import com.sparta.newspeed.common.exception.PasswordMismatchException;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public BoardOneResponseDto createBoard(UpdateBoardRequestDto reqDto, User jwtUser) {

        Board board = new Board(
                jwtUser,
                reqDto.getTitle(),
                reqDto.getContent()
        );

        boardRepository.save(board);

        return new BoardOneResponseDto("201", "게시물 생성 완료", board);
    }

    public BoardOneResponseDto getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
        return new BoardOneResponseDto("200", "특정 게시물 조회 완료", board);
    }

    public BoardListResponseDto getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return new BoardListResponseDto("200", "전체 게시물 조회 완료", boards);
    }

    @Transactional
    public BoardOneResponseDto editBoard(Long id, UpdateBoardRequestDto reqDto, User jwtUser) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
        board.update(
                reqDto.getTitle(),
                reqDto.getContent()
        );
        if (!board.getUser().getId().equals(jwtUser.getId()))
            throw new ClientRequestException("자신이 작성한 게시물만 수정 가능합니다.");

        return new BoardOneResponseDto("200", "게시물 수정 완료", board);
    }

    @Transactional
    public void deleteBoard(Long id, DeleteBoardRequestDto reqDto, User jwtUser) {


        Board board = boardRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));

        if (!board.getUser().getId().equals(jwtUser.getId()))
            throw new ClientRequestException("자신이 작성한 게시물만 수정 가능합니다.");

        checkUserPassword(reqDto.getPassword(), jwtUser);

        boardRepository.delete(board);
    }

    private void checkUserPassword (String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
    }
}
