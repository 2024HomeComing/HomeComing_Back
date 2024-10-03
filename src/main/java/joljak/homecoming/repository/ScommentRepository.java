package joljak.homecoming.repository;

import joljak.homecoming.entity.Scomment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ScommentRepository extends JpaRepository<Scomment, Long> {
    List<Scomment> findBySightingBoardId(Long sightingBoard);

    public Optional<Scomment> findById(Long id);
//    @Transactional
//    @Modifying
//    @Query("DELETE FROM Scomment sc WHERE sc.sightingboard.id = :boardId")
//    void deleteByBoardId(Long boardId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Scomment sc WHERE sc.id = :scommentId")
    void deleteById(Long scommentId);
}
