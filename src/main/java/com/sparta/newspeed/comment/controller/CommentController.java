package com.sparta.newspeed.comment.controller;

import com.sparta.newspeed.comment.dto.CommentOneResponseDto;
import com.sparta.newspeed.comment.dto.CommentRequestDto;
import com.sparta.newspeed.comment.dto.CommentResponseDto;
import com.sparta.newspeed.comment.dto.CommentListResponseDto;

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



    // 1. 댓글 추가
    @PostMapping("/comments")
    public ResponseEntity<CommentOneResponseDto> saveComment(
            @PathVariable Long boardId,
            @Valid @RequestBody CommentRequestDto requestDto,
            @RequestAttribute("user") User jwtUser) {
        CommentOneResponseDto resDto = commentService.saveComment(boardId, requestDto, jwtUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    //2. 댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<CommentListResponseDto> getComments(@PathVariable Long boardId) {
        CommentListResponseDto resDtos = commentService.getCommentsByBoardId(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(resDtos);
    }

    // 3. 댓글 수정
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentOneResponseDto> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto,
            @RequestAttribute("user") User jwtUser) {

        CommentOneResponseDto resDto = commentService.updateComment(commentId, requestDto, jwtUser);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    // 4. 댓글 삭제
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long id, @RequestAttribute("user") User jwtUser) {
        commentService.deleteComment(boardId, id, jwtUser);
        return ResponseEntity.noContent().build();
    }
}