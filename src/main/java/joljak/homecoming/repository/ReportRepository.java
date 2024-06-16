package joljak.homecoming.repository;

import joljak.homecoming.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report,Long> {
    List<Report> findByPetInfoId(Long petInfoId);

    Optional<Report> findById(Long reportId);

}
