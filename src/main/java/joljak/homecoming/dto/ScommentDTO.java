package joljak.homecoming.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScommentDTO {
    private Long id;
    private String writer;    // 작성자
    private String content;
    private String time;      // 댓글 시간
    private String userId;
    private Long sightingId;
}
