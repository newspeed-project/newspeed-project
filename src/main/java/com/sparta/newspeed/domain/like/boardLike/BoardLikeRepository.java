package com.sparta.newspeed.domain.like.boardLike;

import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Long countAllByBoard(Board board);

    void deleteByBoardAndUser(Board board, User jwtUser);

    void deleteAllByBoard(Board board);
}
