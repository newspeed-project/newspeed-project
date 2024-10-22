package com.sparta.newspeed.domain.friend;


import com.sparta.newspeed.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    Friend findByResponseUser(User jwtUser);
}
