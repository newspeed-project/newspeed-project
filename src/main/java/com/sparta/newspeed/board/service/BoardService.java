package com.sparta.newspeed.board.service;

import com.sparta.newspeed.board.dto.*;
import com.sparta.newspeed.comment.dto.LikeResponseDto;
import com.sparta.newspeed.common.PasswordEncoder;
import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.board.BoardRepository;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.common.exception.ResourceNotFoundException;
import com.sparta.newspeed.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public EditBoardResponseDto editBoard(Long id, UpdateBoardRequestDto reqDto, User jwtUser) {

        Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        board.update(
                reqDto.getTitle(),
                reqDto.getContent()
        );
        if (!board.getUser().getId().equals(jwtUser.getId()))
            throw new IllegalArgumentException("자신이 작성한 게시물만 수정 가능합니다.");

        return new EditBoardResponseDto("200", "게시물 수정 완료", board);
    }

    @Transactional
    public void deleteBoard(Long id, DeleteBoardRequestDto reqDto, User jwtUser) {


        Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        if (!board.getUser().getId().equals(jwtUser.getId()))
            throw new IllegalArgumentException("자신이 작성한 게시물만 수정 가능합니다.");

        checkUserPassword(reqDto.getPassword(), jwtUser);

        boardRepository.delete(board);
    }

    private void checkUserPassword (String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }


    @Transactional
    public LikeResponseDto addLikeToBoard(Long boardId, User jwtUser) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("게시물이 존재하지 않습니다."));

        // 이미 좋아요를 눌렀는지 확인
        if (board.getLikedByUsers().contains(jwtUser)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 게시물입니다.");
        }

        board.addLike(jwtUser); // 사용자 추가
        board.incrementLikeCount(); // 좋아요 수 증가
        boardRepository.save(board); // 변경 사항 저장

        return new LikeResponseDto(board.getId(), board.getLikeCount(), true);
    }


    // 게시물 좋아요 취소
    @Transactional
    public LikeResponseDto unlikeBoard(Long boardId, User jwtUser) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 게시물이 존재하지 않습니다."));

        // 좋아요를 누르지 않았으면 예외 발생
        if (!board.getLikedByUsers().contains(jwtUser)) {
            throw new IllegalArgumentException("좋아요를 누르지 않은 게시물입니다.");
        }

        board.removeLike(jwtUser); // 사용자 제거
        board.decrementLikeCount(); // 좋아요 수 감소
        boardRepository.save(board); // 변경 사항 저장

        return new LikeResponseDto(board.getId(), board.getLikeCount(),false);
    }

    // 게시물 좋아요 조회
    public List<User> getLikedUsers(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("게시물이 존재하지 않습니다."));
        return new ArrayList<>(board.getLikedByUsers()); // 좋아요를 누른 사용자 목록 반환
    }

}
