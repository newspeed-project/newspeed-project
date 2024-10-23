package com.sparta.newspeed.like.boardLike.service;

import com.sparta.newspeed.common.exception.ResourceNotFoundException;
import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.board.BoardRepository;
import com.sparta.newspeed.domain.like.boardLike.BoardLike;
import com.sparta.newspeed.domain.like.boardLike.BoardLikeRepository;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.like.boardLike.dto.BoardLikeDefaultResponseDto;
import com.sparta.newspeed.like.boardLike.dto.BoardLikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    public BoardLikeDefaultResponseDto likeBoard(Long boardId, User jwtUser) {
        Board board = findBoardById(boardId);
        BoardLike boardLike = BoardLike.create(board, jwtUser);
        boardLikeRepository.save(boardLike);
        Long count = boardLikeRepository.countAllByBoard(board);
        return new BoardLikeDefaultResponseDto("201", "좋아요 등록 성공", count);
    }

    public BoardLikeDefaultResponseDto getLikeBoard(Long boardId) {
        Board board = findBoardById(boardId);
        Long count = boardLikeRepository.countAllByBoard(board);
        return new BoardLikeDefaultResponseDto("200", "좋아요 조회 성공", count);
    }

    public void unlikeBoard(Long boardId, User jwtUser) {
        Board board = findBoardById(boardId);
        boardLikeRepository.deleteByBoardAndUser(board, jwtUser);
    }

    // ============= 편의 메서드 =============

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new ResourceNotFoundException("해당 ID의 게시물을 찾을 수 없습니다.")
        );
    }
}
