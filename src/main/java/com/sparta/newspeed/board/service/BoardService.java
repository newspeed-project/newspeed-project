package com.sparta.newspeed.board.service;

import com.sparta.newspeed.board.dto.*;
import com.sparta.newspeed.common.PasswordEncoder;
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
    public CreateBoardResponseDto createBoard(UpdateBoardRequestDto reqDto) {
        // todo: 인증 기능이 구현되면 쿠키를 통해 얻은 유저 정보의 Id값으로 바꿔줘야 함.
        User user = userRepository.findById(1L).orElseThrow(IllegalArgumentException::new);

        Board board = new Board(
                user,
                reqDto.getTitle(),
                reqDto.getContent()
        );

        boardRepository.save(board);

        return new CreateBoardResponseDto("201", "게시물 생성 완료", board.getId());
    }

    public BoardResponseDto getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
        return new BoardResponseDto("201", "특정 게시물 조회 완료", board);
    }

    public List<BoardResponseDto> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(board -> new BoardResponseDto("201", "전체 게시물 조회 완료", board))
                .toList();
    }

    @Transactional
    public EditBoardResponseDto editBoard(Long id, UpdateBoardRequestDto reqDto) {

        // todo: 쿠키의 유저id 와 요청하고있는 board의 작성자id가 같은지 검사하는 로직 필요
        Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        board.update(
                reqDto.getTitle(),
                reqDto.getContent()
        );

        return new EditBoardResponseDto("200", "게시물 수정 완료", board);
    }

    @Transactional
    public void deleteBoard(Long id, DeleteBoardRequestDto reqDto) {

        // todo: 쿠키의 유저id 와 요청하고있는 board의 작성자id가 같은지 검사하는 로직 필요
        User user = userRepository.findById(2L).orElseThrow(IllegalArgumentException::new);
        Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        checkUserPassword(reqDto.getPassword(), user);

        boardRepository.delete(board);
    }

    private void checkUserPassword (String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }
}
