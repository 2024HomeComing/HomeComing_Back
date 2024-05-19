package com.example.crud.controller;

import com.example.crud.entity.Board;
import com.example.crud.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    //@PostMapping("/image")
    //public Board insertBoard(@RequestBody Board board, @RequestParam("image") MultipartFile imageFile) throws IOException {
    //    return boardService.insertBoard(board, imageFile);
    //}

    @PostMapping("")
    public Board insertBoard(@RequestBody Board board) {

        return boardService.insertBoard(board);
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
                board.getLastSeenTime(), board.getAdditionInfo());
    }

    @DeleteMapping("/{userId}/{boardId}")
    public void deleteBoard(@PathVariable String userId, @PathVariable Long boardId) {
        boardService.deleteBoard(userId, boardId);
    }
}