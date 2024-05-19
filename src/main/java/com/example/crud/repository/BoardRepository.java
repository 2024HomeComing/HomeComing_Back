package com.example.crud.repository;

import com.example.crud.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Board findByUserId(String userId);

    // 이 메서드는 오늘 날짜로 작성된 게시글 수를 반환합니다.
    @Query("SELECT COUNT(b) FROM Board b WHERE b.createdAt >= CURRENT_DATE")
    Long countPostsToday();

    @Transactional
    @Modifying
    @Query("UPDATE Board b SET b.title = :title, b.age = :age, b.size = :size, b.name = :name, b.characteristics = :characteristics, b.color = :color, b.breed = :breed, b.lastSeenLocation = :lastSeenLocation, b.lastSeenTime = :lastSeenTime, b.additionInfo = :additionInfo  WHERE b.userId = :userId")
    void updateBoardByUserId(String userId, String title, int age, String size, String name, String characteristics, String color, String breed, String lastSeenLocation, String lastSeenTime, String additionInfo);

    @Transactional
    @Modifying
    @Query("DELETE FROM Board u WHERE u.userId = :userId AND u.id = :boardId")
    void deleteBoardByUserIdAndBoardId(String userId, Long boardId);

}
