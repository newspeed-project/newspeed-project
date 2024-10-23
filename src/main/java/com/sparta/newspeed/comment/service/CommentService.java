package com.sparta.newspeed.comment.service;

import com.sparta.newspeed.comment.dto.CommentRequestDto;
import com.sparta.newspeed.comment.dto.CommentResponseDto;
import com.sparta.newspeed.comment.dto.ReadAllCommentResponseDto;
import com.sparta.newspeed.common.exception.ResourceNotFoundException;
import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.board.BoardRepository;
import com.sparta.newspeed.domain.comment.Comment;
import com.sparta.newspeed.domain.comment.CommentRepository;
import com.sparta.newspeed.domain.user.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    //댓글 생성
    @Transactional
    public CommentResponseDto saveComment(Long boardId, CommentRequestDto requestDto, User jwtUser) {
        // 게시물 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 게시물이 존재하지 않습니다. ID: " + boardId));

        // 댓글 저장
        Comment comment = new Comment();
        comment.saveComment(requestDto.getContent(), board, jwtUser);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);  // DTO로 변환하여 반환
    }

    //댓글 조회
    public ReadAllCommentResponseDto getCommentsByBoardId(Long boardId) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        return new ReadAllCommentResponseDto("200", "특정 게시물의 댓글 조회 완료", comments);
    }

    //댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User jwtUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 댓글이 존재하지 않습니다. ID: " + commentId));

        // 권한 검증: 댓글 작성자 또는 게시글 작성자만 수정 가능
        if (!isAuthorizedToModifyOrDelete(comment, jwtUser)) {
            throw new IllegalArgumentException("댓글 수정 권한이 없습니다.");
        }
        
        //댓글 내용 수정
        comment.setContent(requestDto.getContent());

        return updateComment(commentId, requestDto, jwtUser);  // DTO로 변환하여 반환
    }
    // 권한 확인 메서드: 댓글 작성자 또는 게시글 작성자인지 확인
    private boolean isAuthorizedToModifyOrDelete(Comment comment, User jwtUser) {
        return jwtUser.getId().equals(comment.getUser().getId()) ||
                jwtUser.getId().equals(comment.getBoard().getUser().getId());
    }
    // 댓글 삭제
    @Transactional
    public void deleteComment(Long boardId, Long id, User jwtUser) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 댓글이 존재하지 않습니다."));

        if (comment.getBoard().getId().equals(boardId) || jwtUser.getId().equals(comment.getUser().getId())) {
            commentRepository.delete(comment);
        }
    }
}