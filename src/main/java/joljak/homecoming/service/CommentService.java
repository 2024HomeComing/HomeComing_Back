package joljak.homecoming.service;

import joljak.homecoming.entity.Board;
import joljak.homecoming.entity.Comment;
import joljak.homecoming.repository.BoardRepository;
import joljak.homecoming.repository.CommentRepository;
import joljak.homecoming.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    public Comment updateComment(Long id, String text) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(text);
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}