package com.sparta.newspeed.comment.controller;

import com.sparta.newspeed.comment.dto.CommentRequestDto;
import com.sparta.newspeed.comment.dto.ReadAllCommentResponseDto;


import com.sparta.newspeed.domain.comment.Comment;
import com.sparta.newspeed.comment.service.CommentService;
import com.sparta.newspeed.domain.user.User;
import jakarta.validation.Valid;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/board/{boardId}")
public class CommentController {
    private final CommentService commentService;

    // 의존성 주입 - 생성자 주입 방식 사용
//    public CommentController(CommentService commentService) {
//        this.commentService = commentService;
//    }

    // 1. 댓글 추가
    @PostMapping("/comments")
    public Comment saveComment(
            @PathVariable Long boardId,
            @Valid @RequestBody CommentRequestDto requestDto,
            @RequestAttribute("user") User jwtUser) {
        return commentService.saveComment(boardId, requestDto, jwtUser);
    }

    //2. 댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<ReadAllCommentResponseDto> getComments(@PathVariable Long boardId) {
        ReadAllCommentResponseDto comments = commentService.getCommentsByBoardId(boardId);
        return ResponseEntity.ok(comments);
    }


    // 3. 댓글 수정
    @PutMapping("/comments/{id}")
    public Comment updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto,
            @RequestAttribute("user") User jwtUser) {

        return commentService.updateComment(commentId, requestDto, jwtUser);
    }
}

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long id, @RequestAttribute("user") User jwtUser) {
        commentService.deleteComment(boardId, id, jwtUser);
        return ResponseEntity.noContent().build();
    }

}

