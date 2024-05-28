package joljak.homecoming.service;

import joljak.homecoming.entity.Board;
import joljak.homecoming.repository.BoardRepository;
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
    private S3Service s3Service;

    //@Autowired
    //private ImageService imageService;

    //@Autowired
    //private UserRepository userRepository;

    public Long countPostsToday() {
        return boardRepository.countPostsToday();
    }
    public Board insertBoard(Board board, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String key = "images/" + board.getUserId() + "/" + imageFile.getOriginalFilename();
            String imageUrl = s3Service.uploadFile(key, imageFile.getBytes());
            board.setImageUrl(imageUrl);
        }
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
