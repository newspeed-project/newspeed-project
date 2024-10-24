package com.sparta.newspeed.domain.like.commentLike;

import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.comment.Comment;
import com.sparta.newspeed.domain.user.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Table(name = "commentLikes")
@NoArgsConstructor
public class CommentLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "commentId")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    private CommentLike(Comment comment, User user, Board board) {
        this.comment = comment;
        this.user = user;
        this.board = board;
    }

    public static CommentLike create(Comment comment, User jwtUser, Board board) {
        return new CommentLike(comment, jwtUser, board);
    }
}
