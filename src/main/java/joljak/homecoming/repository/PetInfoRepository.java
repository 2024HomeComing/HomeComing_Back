package joljak.homecoming.repository;

import joljak.homecoming.entity.PetInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetInfoRepository extends JpaRepository<PetInfo, Long> {
}
