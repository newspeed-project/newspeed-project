package com.sparta.newspeed.like.commentLike.controller;

import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.like.commentLike.dto.CommentLikeDefaultResponseDto;
import com.sparta.newspeed.like.commentLike.dto.CommentLikeResponseDto;
import com.sparta.newspeed.like.commentLike.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/{boardId}")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<CommentLikeDefaultResponseDto> likeComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @RequestAttribute("user") User jwtUser
    ) {
        CommentLikeDefaultResponseDto resDto = commentLikeService.likeComment(boardId, commentId, jwtUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @GetMapping("/comments/{commentId}/like")
    public ResponseEntity<CommentLikeDefaultResponseDto> getLikeBoard(
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ) {
        CommentLikeDefaultResponseDto resDto = commentLikeService.getLikeComment(boardId, commentId);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @DeleteMapping("/comments/{commentId}/like")
    public ResponseEntity<Void> unlikeBoard(
            @PathVariable Long boardId,
            @PathVariable Long commentId,
            @RequestAttribute("user") User jwtUser
    ) {
        commentLikeService.unlikeComment(boardId, commentId, jwtUser);
        return ResponseEntity.noContent().build();
    }
}
