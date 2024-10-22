package com.sparta.newspeed.comment.controller;

import com.sparta.newspeed.comment.dto.CommentRequestDto;
import com.sparta.newspeed.domain.comment.Comment;
import com.sparta.newspeed.comment.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/board/{boardId}")
public class CommentController {
    private final CommentService commentService;

    // 의존성 주입 - 생성자 주입 방식 사용
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    // 1. 댓글 추가
    @PostMapping("/comments")
    public Comment saveComment(
            @PathVariable Long boardId,
            @Valid @RequestBody CommentRequestDto requestDto) {
        return commentService.saveComment(boardId, requestDto);
    }

    // 2. 게시물에 대한 댓글 조회
    @GetMapping("/comments")
    public List<Comment> getCommentsByBoard(@PathVariable Long scheduleId) {
        return commentService.findCommentsByBoardId(scheduleId);
    }
}
