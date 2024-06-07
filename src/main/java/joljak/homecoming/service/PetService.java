package joljak.homecoming.service;

import joljak.homecoming.entity.PetInfo;
import joljak.homecoming.entity.Report;
import joljak.homecoming.repository.PetInfoRepository;
import joljak.homecoming.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetInfoRepository petInfoRepository;

    @Autowired
    private ReportRepository reportRepository;

    public List<PetInfo> getPetsByUserId(Long userDbId) {
        return petInfoRepository.findByUserId(userDbId);
    }

    public List<Report> getReportsByPetInfoId(Long petInfoId) {
        return reportRepository.findByPetInfoId(petInfoId);
    }

}
