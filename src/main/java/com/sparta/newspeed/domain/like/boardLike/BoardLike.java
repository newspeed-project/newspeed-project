package com.sparta.newspeed.domain.like.boardLike;

import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "boardLikes")
@NoArgsConstructor
public class BoardLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    private BoardLike(Board board, User user) {
        this.board = board;
        this.user = user;
    }

    public static BoardLike create(Board board, User jwtUser) {
        return new BoardLike(board, jwtUser);
    }
}
