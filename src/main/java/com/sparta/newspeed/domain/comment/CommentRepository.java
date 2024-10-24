package com.sparta.newspeed.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sparta.newspeed.domain.board.Board;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardId(Long BoardId);
    void deleteByBoard(Board board);
}
