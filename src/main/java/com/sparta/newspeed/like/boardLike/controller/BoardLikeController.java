package com.sparta.newspeed.like.boardLike.controller;

import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.like.boardLike.dto.BoardLikeDefaultResponseDto;
import com.sparta.newspeed.like.boardLike.dto.BoardLikeResponseDto;
import com.sparta.newspeed.like.boardLike.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @PostMapping("/board/{boardId}/like")
    public ResponseEntity<BoardLikeDefaultResponseDto> likeBoard(
            @PathVariable Long boardId,
            @RequestAttribute("user") User jwtUser
    ) {
        BoardLikeDefaultResponseDto resDto = boardLikeService.likeBoard(boardId, jwtUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @GetMapping("/board/{boardId}/like")
    public ResponseEntity<BoardLikeDefaultResponseDto> getLikeBoard(@PathVariable Long boardId) {
        BoardLikeDefaultResponseDto resDto = boardLikeService.getLikeBoard(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @DeleteMapping("/board/{boardId}/like")
    public ResponseEntity<Void> unlikeBoard(
            @PathVariable Long boardId,
            @RequestAttribute("user") User jwtUser
    ) {
        boardLikeService.unlikeBoard(boardId, jwtUser);
        return ResponseEntity.noContent().build();
    }
}
