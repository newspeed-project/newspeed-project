package com.sparta.newspeed.domain.comment;

import com.sparta.newspeed.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardId(Long BoardId);
    void deleteByUser(User user);
}
