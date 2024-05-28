package joljak.homecoming.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class SightingBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String name;
    private String breed; // 품종
    private String size; // 크기
    private String furColor; // 털 색상
    private String petCharacter; // 특징
    private String location; // 위치
    private String sightingTime; // 시간
    private String contact; // 연락처

    private LocalDateTime createdAt; // 게시글 작성 시간

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
