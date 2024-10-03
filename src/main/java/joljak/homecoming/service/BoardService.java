package joljak.homecoming.service;

import joljak.homecoming.dto.AllboardDTO;
import joljak.homecoming.entity.Board;
import joljak.homecoming.entity.SightingBoard;
import joljak.homecoming.entity.User;
import joljak.homecoming.repository.BoardRepository;
import joljak.homecoming.repository.SightingBoardRepository;
import joljak.homecoming.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    SightingBoardRepository sightingBoardRepository;

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
            String key = "images/" + board.getUser().getProviderId() + "/" + imageFile.getOriginalFilename();
            String imageUrl = s3Service.uploadFile(key, imageFile.getBytes());
            board.setImageUrl(imageUrl);
        }
        return boardRepository.save(board);
    }

    public List<Board> getPostsToday() {
        return boardRepository.findPostsToday();
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public List<Board> getBoardByUserId(long userId) {
        return boardRepository.findByUserId(userId);
    }

    public void updateBoard(String userId,String boardId, String title, String age, String size, String name, String characteristics, String color, String breed, String lastSeenLocation, String lastSeenTime, String additionInfo) {
        boardRepository.updateBoardByUserId(userId, boardId, title, age, size, name, characteristics, color, breed, lastSeenLocation, lastSeenTime, additionInfo);
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElse(null);
    }


    public void deleteBoard(String userId, Long boardId) {

        // userId로 User 엔티티를 조회
        User user = userRepository.findByProviderId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        boardRepository.deleteBoardByUserAndBoardId(user, boardId);
    }

    public List<AllboardDTO> getTodayAllPosts() {
        List<AllboardDTO> allPosts = new ArrayList<>();

        // 실종 게시판의 게시글
        List<Board> boards = boardRepository.findPostsToday();
        for (Board board : boards) {
            allPosts.add(new AllboardDTO(
                    board.getId(),
                    board.getTitle(),
                    board.getBreed(),
                    board.getName(),
                    board.getSize(),
                    board.getAge(),
                    board.getColor(),
                    board.getCharacteristics(),
                    board.getLastSeenLocation(),
                    board.getLastSeenTime(),
                    board.getContact(),
                    board.getAdditionalInfo(),
                    board.getCreatedAt(),
                    board.getImageUrl(),
                    "missing"));
        }

        // 목격 게시판의 게시글
        List<SightingBoard> sightingBoards = sightingBoardRepository.findPostsToday();
        for (SightingBoard sightingBoard : sightingBoards) {
            allPosts.add(new AllboardDTO(
                    sightingBoard.getId(),
                    sightingBoard.getWtitle(),
                    sightingBoard.getWbreed(),
                    sightingBoard.getUsername(),
                    sightingBoard.getWsize(),
                    null,
                    sightingBoard.getWcolor(),
                    sightingBoard.getWcharacteristics(),
                    sightingBoard.getWlastSeenLocation(),
                    sightingBoard.getWlastSeenTime(),
                    sightingBoard.getWcontact(),
                    sightingBoard.getWadditionalInfo(),
                    sightingBoard.getWcreatedAt(),
                    sightingBoard.getWImageUrl(),
                    "sighting"));
        }

        return allPosts;
    }
}
