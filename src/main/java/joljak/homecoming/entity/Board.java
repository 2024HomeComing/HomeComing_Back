package joljak.homecoming.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //품종, 나이, 크기, 이름, 털색, 특징
    private Long id;

    private String username; // 게시글 작성한 유저 이름
    private String age; // 반려동물 나이

    private String title; //제목
    private String size; // 반려동물 사이즈
    private String name; // 반려동물 이름
    private String characteristics; // 반려동물 특징
    private String color; // 반려동물 털색
    private String breed; // 반려동물 품종
    private String lastSeenLocation; // 마지막 확인 장소
    private String lastSeenTime; // 확인 시기
    private String contact; // 연락처//
    private String additionalInfo; //추가정보

    private LocalDateTime createdAt; // 게시글 작성 시간

    private String imageUrl; // 추가된 필드

    @ManyToOne
    private User user;
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
