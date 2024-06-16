package joljak.homecoming.controller;

import joljak.homecoming.dto.SightingBoardDto;
import joljak.homecoming.entity.Board;
import joljak.homecoming.entity.SightingBoard;
import joljak.homecoming.entity.User;
import joljak.homecoming.repository.UserRepository;
import joljak.homecoming.service.SightingBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/sighting")
public class SightingBoardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SightingBoardService sightingBoardService;



    @PostMapping("")
    public ResponseEntity<?> insertSightingReport(@RequestPart("board") SightingBoardDto sightingBoardDto, @RequestPart(value = "images", required = false) MultipartFile imageFile) {
        String providerId = sightingBoardDto.getUserId();
        User user = userRepository.findByProviderId(providerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SightingBoard sightingBoard = getSightingBoard(sightingBoardDto, user);
        try {
            SightingBoard savedSightingBoard = sightingBoardService.insertSightingReport(sightingBoard, imageFile);
            return new ResponseEntity<>(savedSightingBoard, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static SightingBoard getSightingBoard(SightingBoardDto sightingBoardDto, User user) {
        SightingBoard sightingBoard = new SightingBoard();
        sightingBoard.setWtitle(sightingBoardDto.getWtitle());
        sightingBoard.setUsername(user.getName());
        sightingBoard.setWbreed(sightingBoardDto.getWbreed());
        sightingBoard.setWsize(sightingBoardDto.getWsize());
        sightingBoard.setWcolor(sightingBoardDto.getWcolor());
        sightingBoard.setWcharacteristics(sightingBoardDto.getWcharacteristics());
        sightingBoard.setWlastSeenLocation(sightingBoardDto.getWlastSeenLocation());
        sightingBoard.setWlastSeenTime(sightingBoardDto.getWlastSeenTime());
        sightingBoard.setWcontact(sightingBoardDto.getWcontact());
        sightingBoard.setWadditionalInfo(sightingBoardDto.getWadditionalInfo());
        sightingBoard.setUser(user);
        return sightingBoard;
    }


    @GetMapping("")
    public List<SightingBoard> getAllSightingReports() {
        return sightingBoardService.getAllSightingReports();
    }

    @GetMapping("{sightingId}")
    public ResponseEntity<SightingBoard> getSightingBoardById(@PathVariable Long sightingId) {
        SightingBoard sightingBoard = sightingBoardService.getSightingBoardById(sightingId);
        if (sightingBoard != null) {
            return ResponseEntity.ok(sightingBoard);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PutMapping("/{userId}")
//    public void updateSightingReport(@PathVariable String userId, @RequestBody SightingBoard sightingReport) {
//        sightingBoardService.updateSightingReport(userId, sightingReport.getBreed(), sightingReport.getSize(), sightingReport.getFurColor(), sightingReport.getPetCharacter(), sightingReport.getLocation(), sightingReport.getSightingTime(), sightingReport.getContact());
//    }
//
//    @DeleteMapping("/{userId}/{sightingId}")
//    public void deleteSightingReport(@PathVariable String userId, @PathVariable Long sightingId) {
//        sightingBoardService.deleteSightingReport(userId, sightingId);
//    }
//
//
    @GetMapping("/count/today")
    public Long countPostsToday() {
        return sightingBoardService.countPostsToday();
    }
//
//    @PutMapping("/{userId}/{sightingId}")
//    public void updateSightingReport(@PathVariable String userId, @PathVariable Long sightingId, @RequestBody SightingBoard sightingReport) {
//        sightingBoardService.updateSightingReport(userId, sightingId, sightingReport.getBreed(), sightingReport.getSize(), sightingReport.getFurColor(), sightingReport.getPetCharacter(), sightingReport.getLocation(), sightingReport.getSightingTime(), sightingReport.getContact());
//    }


}
