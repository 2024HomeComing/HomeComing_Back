package com.example.crud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // 댓글 작성자 ID
    private String content; // 댓글 내용
    private LocalDateTime createdAt; // 댓글 작성 시간

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board; // 게시글과의 연관 관계
    //private Long board;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
