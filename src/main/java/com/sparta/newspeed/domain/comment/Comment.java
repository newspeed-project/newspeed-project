package com.sparta.newspeed.domain.comment;

import com.sparta.newspeed.domain.TimeStamped;
import com.sparta.newspeed.domain.board.Board;
import com.sparta.newspeed.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String content;

    // 좋아요 수를 저장하는 필드
    @Column(nullable = false)
    private int likeCount = 0; // 초기값을 0으로 설정

    public void saveComment(String content, Board board, User jwtUser) {
        this.content = content;
        this.board = board;
        this.user = jwtUser;
    }

    @ManyToMany
    @JoinTable(
            name = "comment_likes",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedByUsers = new HashSet<>(); // 좋아요를 누른 사용자들

    // 좋아요 수 증가 메서드
    public void incrementLikeCount() {
        this.likeCount++;
    }

    public int getLikeCount() {
        return this.likeCount;
    }


    // 좋아요 취소 메서드
    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    // 좋아요 추가 메서드
    public void addLike(User user) {
        this.likedByUsers.add(user);
    }

    // 좋아요 제거 메서드
    public void removeLike(User user) {
        this.likedByUsers.remove(user);
    }
}
