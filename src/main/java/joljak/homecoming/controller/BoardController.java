package joljak.homecoming.controller;

import joljak.homecoming.dto.Post;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/myboard")
public class BoardController {

    private final List<Post> posts = new ArrayList<>();
    private Long postId = 0L;

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        post.setId(++postId);
        posts.add(post);
        return post;
    }

    @GetMapping("/{author}")
    public List<Post> getPostsByAuthor(@PathVariable String author) {
        return posts.stream()
                .filter(post -> post.getAuthor().equals(author))
                .collect(Collectors.toList());
    }
}
