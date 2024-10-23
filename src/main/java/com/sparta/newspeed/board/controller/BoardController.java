package com.sparta.newspeed.board.controller;

import com.sparta.newspeed.board.dto.*;
import com.sparta.newspeed.board.service.BoardService;
import com.sparta.newspeed.comment.dto.LikeResponseDto;
import com.sparta.newspeed.common.ResponseWrapper;
import com.sparta.newspeed.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/{id}")
    public ResponseEntity<BoardOneResponseDto> getBoard(@PathVariable Long id) {
        BoardOneResponseDto board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }

    @PostMapping("")
    public ResponseEntity<BoardOneResponseDto> createBoard (@RequestBody @Valid UpdateBoardRequestDto reqDto, @RequestAttribute("user") User jwtUser) {
        BoardOneResponseDto resDto = boardService.createBoard(reqDto, jwtUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @GetMapping
    public ResponseEntity<BoardListResponseDto> getAllBoards() {
        BoardListResponseDto boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditBoardResponseDto> editBoard (@PathVariable Long id, @RequestBody @Valid UpdateBoardRequestDto reqDto, @RequestAttribute("user") User jwtUser) {
        EditBoardResponseDto resDto = boardService.editBoard(id, reqDto, jwtUser);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id, @RequestBody @Valid DeleteBoardRequestDto reqDto, @RequestAttribute("user") User jwtUser) {
        boardService.deleteBoard(id, reqDto, jwtUser);
        return ResponseEntity.noContent().build();
    }


    //게시물 좋아요 등록
    @PostMapping("/{boardId}/likes")
    public ResponseEntity<ResponseWrapper<LikeResponseDto>> likeBoard(
            @PathVariable Long boardId, @RequestAttribute("userId") User jwtUser) {
        LikeResponseDto responseDto = boardService.addLikeToBoard(boardId, jwtUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>(201, "좋아요 등록 성공", responseDto));
    }

    //게시물 좋아요 취소
    @DeleteMapping("/{boardId}/likes")
    public LikeResponseDto unlikeBoard(@PathVariable Long boardId, @RequestAttribute("user") User jwtUser) {
        return boardService.unlikeBoard(boardId, jwtUser);
    }


    // 게시물 좋아요 조회
    @GetMapping("/{boardId}/likes")
    public ResponseEntity<ResponseWrapper<List<User>>> getLikedUsers(@PathVariable Long boardId) {
        List<User> likedUsers = boardService.getLikedUsers(boardId);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "좋아요를 누른 사용자 조회 성공", likedUsers));
    }
}
