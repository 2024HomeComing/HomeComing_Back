package joljak.homecoming.repository;

import joljak.homecoming.entity.Board;
import joljak.homecoming.entity.SightingBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SightingBoardRepository extends JpaRepository<SightingBoard, Long> {


    Optional<SightingBoard> findByUserId(Long userId);
    Optional<SightingBoard> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT COUNT(s) FROM SightingBoard s WHERE s.wcreatedAt >= CURRENT_DATE")
    Long countPostsToday();
}
