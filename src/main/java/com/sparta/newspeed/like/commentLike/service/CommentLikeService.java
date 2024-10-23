package com.sparta.newspeed.like.commentLike.service;

import com.sparta.newspeed.common.exception.ResourceNotFoundException;
import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.board.BoardRepository;
import com.sparta.newspeed.domain.comment.Comment;
import com.sparta.newspeed.domain.comment.CommentRepository;
import com.sparta.newspeed.domain.like.commentLike.CommentLike;
import com.sparta.newspeed.domain.like.commentLike.CommentLikeRepository;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.like.commentLike.dto.CommentLikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public CommentLikeResponseDto likeComment(Long boardId, Long commentId, User jwtUser) {
        Board board = findBoardById(boardId);
        Comment comment = findCommentById(commentId);
        CommentLike commentLike = CommentLike.create(comment, jwtUser);
        commentLikeRepository.save(commentLike);
        Long count = commentLikeRepository.countAllByComment(comment);
        return new CommentLikeResponseDto(count);
    }

    public CommentLikeResponseDto getLikeComment(Long boardId, Long commentId) {
        Board board = findBoardById(boardId);
        Comment comment = findCommentById(commentId);
        Long count = commentLikeRepository.countAllByComment(comment);
        return new CommentLikeResponseDto(count);
    }

    public void unlikeComment(Long boardId, Long commentId, User jwtUser) {
        Board board = findBoardById(boardId);
        Comment comment = findCommentById(commentId);
        commentLikeRepository.deleteByCommentAndUser(comment, jwtUser);
    }

    // ============= 편의 메서드 =============

    private Board findBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new ResourceNotFoundException("해당 ID의 게시물을 찾을 수 없습니다.")
        );
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("해당 댓글이 존재하지 않습니다.")
        );
    }
}
