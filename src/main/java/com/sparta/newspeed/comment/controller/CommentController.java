package com.sparta.newspeed.comment.controller;

import com.sparta.newspeed.comment.service.CommentService;
import com.sparta.newspeed.comment.dto.ReadAllCommentResponseDto;
import com.sparta.newspeed.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board/{boardId}")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<ReadAllCommentResponseDto> getComments(@PathVariable Long boardId) {
        ReadAllCommentResponseDto comments = commentService.getCommentsByBoardId(boardId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long id, @RequestAttribute("user") User jwtUser) {
        commentService.deleteComment(boardId, id, jwtUser);
        return ResponseEntity.noContent().build();
    }

}
