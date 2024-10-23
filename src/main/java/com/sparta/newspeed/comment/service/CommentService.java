package com.sparta.newspeed.comment.service;

import com.sparta.newspeed.comment.dto.CommentOneResponseDto;
import com.sparta.newspeed.comment.dto.CommentRequestDto;
import com.sparta.newspeed.comment.dto.CommentResponseDto;
import com.sparta.newspeed.comment.dto.CommentListResponseDto;
import com.sparta.newspeed.common.exception.ClientRequestException;
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

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    //댓글 생성
    @Transactional
    public CommentOneResponseDto saveComment(Long boardId, CommentRequestDto requestDto, User jwtUser) {
        // 게시물 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 게시물이 존재하지 않습니다. ID: " + boardId));

        // 댓글 저장
        Comment comment = new Comment();
        comment.saveComment(requestDto.getContent(), board, jwtUser);

        commentRepository.save(comment);

        return new CommentOneResponseDto("201", "댓글 등록 성공", comment);  // DTO로 변환하여 반환
    }

    //댓글 조회
    public CommentListResponseDto getCommentsByBoardId(Long boardId) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        return new CommentListResponseDto("200", "특정 게시물의 댓글 조회 완료", comments);
    }

    //댓글 수정
    @Transactional
    public CommentOneResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User jwtUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 댓글이 존재하지 않습니다. ID: " + commentId));

        // 권한 검증: 댓글 작성자 또는 게시글 작성자만 수정 가능
        if (!isAuthorizedToModifyOrDelete(comment, jwtUser)) {
            throw new ClientRequestException("댓글 작성자 또는 게시글 작성자가 아닌 경우 댓글 수정을 할 수 없습니다.");
        }
        
        //댓글 내용 수정
        comment.setContent(requestDto.getContent());
        commentRepository.save(comment);
        return new CommentOneResponseDto("200", "특정 게시물의 댓글 수정 완료", comment);
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