package joljak.homecoming.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {
    private Long id;

    private String author;

    private String title;

    private String content;
}
