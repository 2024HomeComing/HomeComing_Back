package joljak.homecoming.repository;

import joljak.homecoming.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Long> {
    List<Report> findByPetInfoId(Long petInfoId);
}