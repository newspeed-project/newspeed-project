package com.sparta.newspeed.domain.comment;

import com.sparta.newspeed.domain.TimeStamped;
import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private String content;

    public void saveComment(String content, Board board, User jwtUser) {
        this.content = content;
        this.board = board;
        this.user = jwtUser;
    }

    public void update(String content) {
        this.content = content;
    }
}
