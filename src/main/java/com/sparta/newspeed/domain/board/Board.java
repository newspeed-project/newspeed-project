package com.sparta.newspeed.domain.board;

import com.sparta.newspeed.domain.TimeStamped;
import com.sparta.newspeed.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "boards")
@NoArgsConstructor
public class Board extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private Board(User user , String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public static Board createBoard(User jwtUser, String title, String content) {
        return new Board(jwtUser, title, content);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}