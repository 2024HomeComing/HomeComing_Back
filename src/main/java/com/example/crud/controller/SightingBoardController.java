package com.example.crud.controller;

import com.example.crud.entity.Board;
import com.example.crud.entity.SightingBoard;
import com.example.crud.service.SightingBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sighting")
public class SightingBoardController {

    @Autowired
    private SightingBoardService sightingBoardService;



    @PostMapping("")
    public SightingBoard insertSightingReport(@RequestBody SightingBoard sightingReport) {
        return sightingBoardService.insertSightingReport(sightingReport);
    }

    @GetMapping("")
    public List<SightingBoard> getAllSightingReports() {
        return sightingBoardService.getAllSightingReports();
    }

    @PutMapping("/{userId}")
    public void updateSightingReport(@PathVariable String userId, @RequestBody SightingBoard sightingReport) {
        sightingBoardService.updateSightingReport(userId, sightingReport.getBreed(), sightingReport.getSize(), sightingReport.getFurColor(), sightingReport.getPetCharacter(), sightingReport.getLocation(), sightingReport.getSightingTime(), sightingReport.getContact());
    }

    @DeleteMapping("/{userId}/{sightingId}")
    public void deleteSightingReport(@PathVariable String userId, @PathVariable Long sightingId) {
        sightingBoardService.deleteSightingReport(userId, sightingId);
    }


    @GetMapping("/count/today")
    public Long countPostsToday() {
        return sightingBoardService.countPostsToday();
    }

    @PutMapping("/{userId}/{sightingId}")
    public void updateSightingReport(@PathVariable String userId, @PathVariable Long sightingId, @RequestBody SightingBoard sightingReport) {
        sightingBoardService.updateSightingReport(userId, sightingId, sightingReport.getBreed(), sightingReport.getSize(), sightingReport.getFurColor(), sightingReport.getPetCharacter(), sightingReport.getLocation(), sightingReport.getSightingTime(), sightingReport.getContact());
    }


}
