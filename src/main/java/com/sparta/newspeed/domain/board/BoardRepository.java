package com.sparta.newspeed.domain.board;

import com.sparta.newspeed.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByUserIdIn(List<Long> userIds); // 친구 게시물 조회
    Page<Board> findByUserIdNotIn(List<Long> userIds, Pageable pageable); // 비친구 게시물 조회
    Page<Board> findAll(Pageable pageable);
    void deleteByUser(User user);

}