package com.sparta.newspeed.domain.board;

import com.sparta.newspeed.domain.TimeStamped;
import com.sparta.newspeed.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "boards")
@NoArgsConstructor
public class Board extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int likeCount = 0; // 좋아요 수

    public Board(User user , String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @ManyToMany
    @JoinTable(
            name = "board_likes",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedByUsers = new HashSet<>(); // 좋아요를 누른 사용자들

    // 좋아요 수 증가 메서드
    public void incrementLikeCount() {
        this.likeCount++;
    }

    // 좋아요 수 감소 메서드
    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    // 좋아요 추가 메서드
    public void addLike(User user) {
        this.likedByUsers.add(user);
    }

    // 좋아요 수 조회 메서드
    public int getLikeCount() {
        return likedByUsers.size();
    }

    // 좋아요 제거 메서드
    public void removeLike(User user) {
        this.likedByUsers.remove(user);
    }

}