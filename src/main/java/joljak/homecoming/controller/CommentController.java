package joljak.homecoming.controller;

import joljak.homecoming.dto.CommentDto;
import joljak.homecoming.entity.Board;
import joljak.homecoming.entity.Comment;
import joljak.homecoming.entity.User;
import joljak.homecoming.repository.BoardRepository;
import joljak.homecoming.repository.UserRepository;
import joljak.homecoming.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @PostMapping("")
    public ResponseEntity<?> addComment(@RequestBody CommentDto commentDto) {
        try {
            Optional<User> userOptional = userRepository.findByProviderId(commentDto.getUserId());
            if (!userOptional.isPresent()) { // 값이 없는 경우
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            User user = userOptional.get(); // 값이 존재하는 경우

            Board board = boardRepository.findById(commentDto.getBoardId()).orElse(null);
            if (board == null) {
                return new ResponseEntity<>("Board not found", HttpStatus.NOT_FOUND);
            }

            Comment comment = new Comment();
            // DateTimeFormatter를 사용하여 원하는 형식으로 날짜 파싱
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime createdAt = LocalDateTime.parse(commentDto.getTime(), formatter);

            comment.setTime(createdAt); // 파싱된 시간 설정
            comment.setWriter(user.getName());
            comment.setContent(commentDto.getContent());
            comment.setUser(user);
            comment.setBoard(board);

            Comment savedComment = commentService.saveComment(comment);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // 서버 로그에 에러 스택 트레이스 출력
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> getCommentsByBoardId(@PathVariable Long boardId) {
        try {
            List<Comment> comments = commentService.getCommentsByBoardId(boardId);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @RequestParam String userId) {
        try {
            // 삭제하려는 댓글을 ID로 찾음
            Comment existingComment = commentService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));

            // 요청한 사용자의 userId와 댓글 작성자의 userId를 비교
            if (!existingComment.getUser().getProviderId().equals(userId)) {
                return new ResponseEntity<>("You are not allowed to delete this comment", HttpStatus.FORBIDDEN);
            }

            // 본인의 댓글이면 삭제 진행
            commentService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}