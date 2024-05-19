package com.example.crud.repository;

import com.example.crud.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 추가적인 메소드가 필요하다면 여기에 선언
    User findByUserId(String userId);
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.userPw = :userPw WHERE u.userId = :userId")
    void updateUserByUserId(String userId, String userPw);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.userId = :userId")
    void deleteUserByUserId(String userId);
}