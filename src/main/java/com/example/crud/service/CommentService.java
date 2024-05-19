package com.example.crud.service;

import com.example.crud.entity.Board;
import com.example.crud.entity.Comment;
import com.example.crud.repository.BoardRepository;
import com.example.crud.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BoardRepository boardRepository;

    public Comment addComment(Long boardId, String userId, String content) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid board ID: " + boardId);
        }

        Board board = boardOptional.get();
        Comment comment = new Comment();
        comment.setBoard(board);
        comment.setUserId(userId);
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByBoardId(Long boardId) {
        Optional<Board> boardOptional = boardRepository.findById(boardId);
        if (boardOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid board ID: " + boardId);
        }

        Board board = boardOptional.get();
        return commentRepository.findByBoard(board);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void deleteCommentsByBoardId(Long boardId) {
        commentRepository.deleteByBoardId(boardId);
    }

    public Comment updateComment(Long commentId, String content) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid comment ID: " + commentId);
        }

        Comment comment = commentOptional.get();
        comment.setContent(content);
        return commentRepository.save(comment);
    }
}
