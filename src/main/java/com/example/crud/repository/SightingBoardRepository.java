package com.example.crud.repository;

import com.example.crud.entity.Board;
import com.example.crud.entity.SightingBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SightingBoardRepository extends JpaRepository<SightingBoard, Long> {

    Optional<SightingBoard> findByUserId(String userId);
    Optional<SightingBoard> findByIdAndUserId(Long id, String userId);

    @Query("SELECT COUNT(s) FROM SightingBoard s WHERE s.createdAt >= CURRENT_DATE")
    Long countPostsToday();
}
