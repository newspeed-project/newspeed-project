package com.sparta.newspeed.comment.service;

import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.comment.dto.ReadAllCommentResponseDto;
import com.sparta.newspeed.domain.comment.Comment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sparta.newspeed.domain.comment.CommentRepository;
import com.sparta.newspeed.common.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public ReadAllCommentResponseDto getCommentsByBoardId(Long boardId) {
        List<Comment> comments = commentRepository.findAllByBoardId(boardId);
        return new ReadAllCommentResponseDto("200", "특정 게시물의 댓글 조회 완료", comments);
    }

    @Transactional
    public void deleteComment(Long boardId, Long id, User jwtUser) {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("해당 댓글이 존재하지 않습니다."));

            if (comment.getBoard().getId().equals(boardId) || jwtUser.getId().equals(comment.getUser().getId())) {
                commentRepository.delete(comment);
            }
    }
}
