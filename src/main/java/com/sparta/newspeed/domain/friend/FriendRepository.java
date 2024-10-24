package com.sparta.newspeed.domain.friend;


import com.sparta.newspeed.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    /*
    * req 혹은 res 컬럼이 userId인 경우의 쿼리
    * SELECT CASE WHEN request_user_id = {userId} THEN response_user_id
    *             WHEN response_user_id = {userId} THEN request_user_id
    *             END AS friendId
    * FROM Friend
    * WHERE status = ACCEPTED AND (request_user_id = {userId} OR response_user_id = {userId})
    * */
    @Query("SELECT CASE " +
            "WHEN f.requestUser.id = :userId THEN f.responseUser.id " +
            "WHEN f.responseUser.id = :userId then f.requestUser.id " +
            "END AS friendId " +
            "FROM Friend f " +
            "WHERE f.status = :status AND (f.requestUser.id = :userId OR f.responseUser.id = :userId)")
    List<Long> getFriendIds(@Param("userId") Long userId, @Param("status") RequestStatus status);

    Optional<Friend> findByRequestUserAndResponseUser(User requestUser, User responseUser);

    List<Friend> findByResponseUserAndStatus(User responseUser, RequestStatus status);

    @Query("SELECT CASE WHEN f.requestUser.id = :userId THEN f.responseUser.id ELSE f.requestUser.id END " +
            "FROM Friend f WHERE (f.requestUser.id = :userId OR f.responseUser.id = :userId) " +
            "AND f.status = 'ACCEPTED'")
    List<Long> findAcceptedFriendIds(Long userId);

    List<Friend> findByRequestUserOrResponseUser(User requestUser, User responseUser);


    List<Friend> findByRequestUser(User user);
    List<Friend> findByResponseUser(User user);

}
