package joljak.homecoming.service;

import joljak.homecoming.entity.Board;
import joljak.homecoming.entity.SightingBoard;
import joljak.homecoming.repository.SightingBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SightingBoardService {

    @Autowired
    private SightingBoardRepository sightingBoardRepository;

    @Autowired
    private SightingBoardRepository sightingReportRepository;


    public Long countPostsToday() {
        return sightingBoardRepository.countPostsToday();
    }


    public SightingBoard insertSightingReport(SightingBoard sightingReport) {
        return sightingReportRepository.save(sightingReport);
    }

    public List<SightingBoard> getAllSightingReports() {
        return sightingReportRepository.findAll();
    }

    public SightingBoard getSightingReportByUserId(String userId) {
        Optional<SightingBoard> sightingReportOptional = sightingReportRepository.findByUserId(userId);
        if (sightingReportOptional.isEmpty()) {
            throw new IllegalArgumentException("Sighting report with User ID " + userId + " not found");
        }
        return sightingReportOptional.get();
    }

    public void updateSightingReport(String userId, String breed, String size, String furColor, String petCharacter, String location, String sightingTime, String contact) {
        Optional<SightingBoard> existingReportOptional = sightingReportRepository.findByUserId(userId);
        if (existingReportOptional.isPresent()) {
            SightingBoard existingReport = existingReportOptional.get();
            existingReport.setBreed(breed);
            existingReport.setSize(size);
            existingReport.setFurColor(furColor);
            existingReport.setPetCharacter(petCharacter);
            existingReport.setLocation(location);
            existingReport.setSightingTime(sightingTime);
            existingReport.setContact(contact);
            sightingReportRepository.save(existingReport);
        } else {
            throw new IllegalArgumentException("Sighting report with User ID " + userId + " not found");
        }
    }

    public void deleteSightingReport(String userId, Long sightingId) {
        Optional<SightingBoard> existingReportOptional = sightingReportRepository.findByIdAndUserId(sightingId, userId);
        if (existingReportOptional.isPresent()) {
            sightingReportRepository.deleteById(sightingId);
        } else {
            throw new IllegalArgumentException("Sighting report with ID " + sightingId + " and User ID " + userId + " not found");
        }
    }

    public void updateSightingReport(String userId, Long sightingBoardId, String breed, String size, String furColor, String petCharacter, String location, String sightingTime, String contact) {
        Optional<SightingBoard> existingReportOptional = sightingReportRepository.findByIdAndUserId(sightingBoardId, userId);
        if (existingReportOptional.isPresent()) {
            SightingBoard existingReport = existingReportOptional.get();
            existingReport.setBreed(breed);
            existingReport.setSize(size);
            existingReport.setFurColor(furColor);
            existingReport.setPetCharacter(petCharacter);
            existingReport.setLocation(location);
            existingReport.setSightingTime(sightingTime);
            existingReport.setContact(contact);
            sightingReportRepository.save(existingReport);
        } else {
            throw new IllegalArgumentException("Sighting report with ID " + sightingBoardId + " and User ID " + userId + " not found");
        }
    }



}
