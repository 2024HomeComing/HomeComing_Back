package joljak.homecoming.controller;

import joljak.homecoming.entity.Comment;
import joljak.homecoming.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{boardId}")
    public Comment addComment(@PathVariable Long boardId, @RequestBody Comment comment) {
        return commentService.addComment(boardId, comment.getUserId(), comment.getContent());
    }

    @GetMapping("/{boardId}")
    public List<Comment> getCommentsByBoardId(@PathVariable Long boardId) {
        return commentService.getCommentsByBoardId(boardId);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@PathVariable Long commentId, @RequestBody Comment comment) {
        return commentService.updateComment(commentId, comment.getContent());
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    @DeleteMapping("/board/{boardId}")
    public void deleteCommentsByBoardId(@PathVariable Long boardId) {
        commentService.deleteCommentsByBoardId(boardId);
    }
}
