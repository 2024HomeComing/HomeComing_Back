package com.example.crud.service;

import com.example.crud.entity.Board;
import com.example.crud.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    private ImageService imageService;

    //@Autowired
    //private UserRepository userRepository;

    public Long countPostsToday() {
        return boardRepository.countPostsToday();
    }



    public Board insertBoard(Board board) {
        return boardRepository.save(board);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getBoardByUserId(String userId) {
        return boardRepository.findByUserId(userId);
    }

    public void updateBoard(String userId,String title, int age, String size, String name, String characteristics, String color, String breed, String lastSeenLocation, String lastSeenTime, String additionInfo) {
        boardRepository.updateBoardByUserId(userId, title, age, size, name, characteristics, color, breed, lastSeenLocation, lastSeenTime, additionInfo);
    }



    public void deleteBoard(String userId, Long boardId) {
        boardRepository.deleteBoardByUserIdAndBoardId(userId, boardId);
    }
}
