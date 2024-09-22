package joljak.homecoming.repository;

import joljak.homecoming.entity.PetInfo;
import joljak.homecoming.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetInfoRepository extends JpaRepository<PetInfo, Long> {
    List<PetInfo> findByUserId(Long userId);

    Optional<PetInfo> findById(Long petInfoId);
}
