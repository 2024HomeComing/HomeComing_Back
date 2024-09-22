package joljak.homecoming.controller;

import joljak.homecoming.dto.CommentDto;
import joljak.homecoming.dto.ScommentDTO;
import joljak.homecoming.entity.*;
import joljak.homecoming.repository.BoardRepository;
import joljak.homecoming.repository.SightingBoardRepository;
import joljak.homecoming.repository.UserRepository;
import joljak.homecoming.service.CommentService;
import joljak.homecoming.service.ScommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Scomments")
public class ScommentController {
    @Autowired
    private ScommentService scommentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SightingBoardRepository sightingBoardRepository;

    @PostMapping("")
    public ResponseEntity<?> addScomment(@RequestBody ScommentDTO scommentDto) {
        try {
            Optional<User> userOptional = userRepository.findByProviderId(scommentDto.getUserId());
            if (!userOptional.isPresent()) { // 값이 없는 경우
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            User user = userOptional.get(); // 값이 존재하는 경우

            SightingBoard sightingBoard = sightingBoardRepository.findById(scommentDto.getSightingId()).orElse(null);
            if (sightingBoard == null) {
                return new ResponseEntity<>("Board not found", HttpStatus.NOT_FOUND);
            }

            Scomment scomment = new Scomment();
            // DateTimeFormatter를 사용하여 원하는 형식으로 날짜 파싱
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime createdAt = LocalDateTime.parse(scommentDto.getTime(), formatter);

            scomment.setTime(createdAt); // 파싱된 시간 설정
            scomment.setWriter(user.getName());
            scomment.setContent(scommentDto.getContent());
            scomment.setUser(user);
            scomment.setSightingBoard(sightingBoard);

            Scomment savedScomment = scommentService.saveScomment(scomment);
            return new ResponseEntity<>(savedScomment, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // 서버 로그에 에러 스택 트레이스 출력
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/board/{sightingId}")
    public ResponseEntity<?> getScommentsByBoardId(@PathVariable Long sightingId) {
        try {
            List<Scomment> scomments = scommentService.getScommentsByBoardId(sightingId);
            return new ResponseEntity<>(scomments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateScomment(@PathVariable Long id, @RequestBody CommentDto scommentDto) {
        try {
            Scomment updatedScomment = scommentService.updateScomment(id, scommentDto.getContent());
            return new ResponseEntity<>(updatedScomment, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteScomment(@PathVariable Long id) {
        try {
            scommentService.deleteScomment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
