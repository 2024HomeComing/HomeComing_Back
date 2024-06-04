package joljak.homecoming.controller;

import joljak.homecoming.dto.BoardDto;
import joljak.homecoming.entity.Board;
import joljak.homecoming.entity.User;
import joljak.homecoming.repository.UserRepository;
import joljak.homecoming.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/boards")
public class    BoardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardService boardService;

    //@PostMapping("/image")
    //public Board insertBoard(@RequestBody Board board, @RequestParam("image") MultipartFile imageFile) throws IOException {
    //    return boardService.insertBoard(board, imageFile);
    //}

    @PostMapping("")
    public ResponseEntity<?> insertBoard(@RequestPart("board") BoardDto boardDto, @RequestPart(value = "images", required = false) MultipartFile imageFile) {
        String providerId = boardDto.getUserId();
        User user = userRepository.findByProviderId(providerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Board board = getBoard(boardDto, user);
        try {
            Board savedBoard = boardService.insertBoard(board, imageFile);
            return new ResponseEntity<>(savedBoard, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static Board getBoard(BoardDto boardDto, User user) {
        Board board = new Board();
        board.setUsername(user.getName());
        board.setName(boardDto.getName());
        board.setAge(boardDto.getAge());
        board.setBreed(boardDto.getBreed());
        board.setColor(boardDto.getColor());
        board.setSize(boardDto.getSize());
        board.setTitle(boardDto.getTitle());
        board.setContact(boardDto.getContact());
        board.setCharacteristics(boardDto.getCharacteristics());
        board.setAdditionalInfo(boardDto.getAdditionalInfo());
        board.setLastSeenLocation(boardDto.getLastSeenLocation());
        board.setLastSeenTime(boardDto.getLastSeenTime());
        board.setImageUrl(boardDto.getImageUrl());
        board.setUser(user);
        return board;
    }

    @GetMapping("/count/today")
    public Long countPostsToday() {
        return boardService.countPostsToday();
    }

    @GetMapping("")
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{userId}")
    public Board getBoardByUserId (@PathVariable String userId) {

        return boardService.getBoardByUserId(userId);
    }

    @PutMapping("/{userId}")
    public void updateBoard(@PathVariable String userId, @RequestBody Board board) {
        boardService.updateBoard(userId,board.getTitle(), board.getAge(), board.getSize(), board.getName(), board.getCharacteristics(), board.getColor(), board.getBreed(), board.getLastSeenLocation(),
                board.getLastSeenTime(), board.getAdditionalInfo());
    }

    @DeleteMapping("/{userId}/{boardId}")
    public void deleteBoard(@PathVariable String userId, @PathVariable Long boardId) {
        boardService.deleteBoard(userId, boardId);
    }
}