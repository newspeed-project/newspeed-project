package com.sparta.newspeed.domain.like.commentLike;

import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.comment.Comment;
import com.sparta.newspeed.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Long countAllByComment(Comment comment);
    void deleteByCommentAndUser(Comment comment, User jwtUser);

    void deleteAllByBoard(Board board);
}
