package com.sparta.newspeed.domain.friend;

import com.sparta.newspeed.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestUserId", nullable = false)
    private User requestUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responseUserId")
    private User responseUser;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RequestStatus status;

    public void makeFriend(User jwtUser, User targetUser) {
        this.requestUser = jwtUser;
        this.responseUser = targetUser;
        this.status = RequestStatus.PENDING;
    }

    public void accept() {
        this.status = RequestStatus.ACCEPTED;
    }
}
