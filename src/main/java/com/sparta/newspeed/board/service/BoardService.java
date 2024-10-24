package com.sparta.newspeed.board.service;

import com.sparta.newspeed.board.dto.*;
import com.sparta.newspeed.common.PasswordEncoder;
import com.sparta.newspeed.common.exception.ClientRequestException;
import com.sparta.newspeed.common.exception.PasswordMismatchException;
import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.board.BoardRepository;
import com.sparta.newspeed.domain.comment.CommentRepository;
import com.sparta.newspeed.domain.friend.FriendRepository;
import com.sparta.newspeed.domain.like.boardLike.BoardLikeRepository;
import com.sparta.newspeed.domain.like.commentLike.CommentLikeRepository;
import com.sparta.newspeed.domain.user.User;
import com.sparta.newspeed.common.exception.ResourceNotFoundException;
import com.sparta.newspeed.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FriendRepository friendRepository;
    private final CommentRepository commentRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public BoardOneResponseDto createBoard(UpdateBoardRequestDto reqDto, User jwtUser) {

        Board board = new Board(
                jwtUser,
                reqDto.getTitle(),
                reqDto.getContent()
        );

        boardRepository.save(board);

        return new BoardOneResponseDto("201", "게시물 생성 완료", board);
    }

    public BoardOneResponseDto getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
        return new BoardOneResponseDto("200", "특정 게시물 조회 완료", board);
    }

    public BoardListResponseDto getAllBoards(User jwtUser, int page) {
        List<Long> friendIds = friendRepository.findAcceptedFriendIds(jwtUser.getId());
        Pageable pageable = PageRequest.of(page, 10, Sort.by("modifiedAt").descending());

        List<Board> addBoards;

        if (friendIds.isEmpty()) {
            Page<Board> nonFriendBoards = boardRepository.findAll(pageable);
            addBoards = nonFriendBoards.getContent();
        } else {
            List<Board> friendBoards = boardRepository.findByUserIdIn(friendIds);
            friendBoards.sort(Comparator.comparing(Board::getModifiedAt).reversed());

            int remainingSlots = 10 - friendBoards.size();
            Page<Board> nonFriendBoardsPage = boardRepository.findByUserIdNotIn(friendIds, PageRequest.of(0, remainingSlots, Sort.by("modifiedAt").descending()));

            addBoards = new ArrayList<>(friendBoards);
            addBoards.addAll(nonFriendBoardsPage.getContent());
        }
        return new BoardListResponseDto("200", "전체 게시물 조회 완료", addBoards);
    }

    @Transactional
    public BoardOneResponseDto editBoard(Long id, UpdateBoardRequestDto reqDto, User jwtUser) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));
        board.update(
                reqDto.getTitle(),
                reqDto.getContent()
        );
        if (!board.getUser().getId().equals(jwtUser.getId()))
            throw new ClientRequestException("자신이 작성한 게시물만 수정 가능합니다.");

        return new BoardOneResponseDto("200", "게시물 수정 완료", board);
    }

    @Transactional
    public void deleteBoard(Long id, DeleteBoardRequestDto reqDto, User jwtUser) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));

        if (!board.getUser().getId().equals(jwtUser.getId()))
            throw new ClientRequestException("자신이 작성한 게시물만 수정 가능합니다.");

        checkUserPassword(reqDto.getPassword(), jwtUser);

        commentRepository.deleteByBoard(board);

        boardLikeRepository.deleteAllByBoard(board);
        commentLikeRepository.deleteAllByBoard(board);

        boardRepository.delete(board);
    }

    private void checkUserPassword (String password, User user) {
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
    }
}
