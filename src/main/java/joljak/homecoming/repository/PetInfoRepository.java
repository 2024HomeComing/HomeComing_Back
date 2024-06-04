package joljak.homecoming.repository;

import joljak.homecoming.entity.PetInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetInfoRepository extends JpaRepository<PetInfo, Long> {
    List<PetInfo> findByUserId(Long userId);
}
