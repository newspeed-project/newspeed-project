package com.sparta.newspeed.domain.user;

import com.sparta.newspeed.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username); // 사용자 이름이 존재하는지 확인하는 메소드
}
