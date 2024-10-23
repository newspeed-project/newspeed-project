package com.sparta.newspeed.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByUserIdIn(List<Long> userIds); // 친구 게시물 조회
    List<Board> findByUserIdNotIn(List<Long> userIds); // 비친구 게시물 조회

}