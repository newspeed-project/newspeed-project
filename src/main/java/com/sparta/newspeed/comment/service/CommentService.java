package com.sparta.newspeed.comment.service;

import com.sparta.newspeed.comment.dto.CommentRequestDto;
import com.sparta.newspeed.comment.dto.CommentResponseDto;
import com.sparta.newspeed.comment.dto.LikeResponseDto;
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

import java.util.ArrayList;
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

        commentRepository.save(comment);

        return new CommentResponseDto(comment);  // DTO로 변환하여 반환
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
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
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


    // 댓글 좋아요 등록
    @Transactional
    public LikeResponseDto addLikeToComment(Long commentId, User jwtUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("댓글이 존재하지 않습니다."));

        // 사용자가 이미 좋아요를 누른 경우 예외 처리
        if (comment.getLikedByUsers().contains(jwtUser)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 댓글입니다.");
        }
        comment.addLike(jwtUser); // 사용자 추가
        comment.incrementLikeCount(); // 좋아요 수 증가

        return new LikeResponseDto(comment.getId(),  comment.getLikeCount(), true);
    }


    //댓글 좋아요 취소
    @Transactional
    public LikeResponseDto unlikeComment(Long commentId, User jwtUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 댓글이 존재하지 않습니다."));

        // 좋아요 취소
        if (!comment.getLikedByUsers().contains(jwtUser)) {
            throw new IllegalArgumentException("좋아요를 누르지 않은 댓글입니다.");
        }

        // 좋아요 제거
        comment.removeLike(jwtUser); // 사용자 제거
        comment.decrementLikeCount(); // 좋아요 수 감소
        commentRepository.save(comment); // 저장


        return new LikeResponseDto(comment.getId(), comment.getLikeCount(), false);
    }

    // 댓글 좋아요 조회
    public List<User> getLikedUsers(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("댓글이 존재하지 않습니다."));
        return new ArrayList<>(comment.getLikedByUsers()); // 좋아요를 누른 사용자 목록 반환
    }

}