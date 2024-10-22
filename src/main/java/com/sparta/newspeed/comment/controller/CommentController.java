package com.sparta.newspeed.comment.controller;

import com.sparta.newspeed.comment.service.CommentService;
import com.sparta.newspeed.comment.dto.ReadAllCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{boardId}/comments")
    public ResponseEntity<List<ReadAllCommentResponseDto>> getComments(@PathVariable Long boardId) {
        List<ReadAllCommentResponseDto> comments = commentService.getCommentsByBoardId(boardId);
        return ResponseEntity.ok(comments);
    }
}
