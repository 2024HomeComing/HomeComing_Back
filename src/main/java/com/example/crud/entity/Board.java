package com.example.crud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //품종, 나이, 크기, 이름, 털색, 특징
    private Long id;
    private String userId; // 게시글 작성한 유저 id
    private int age; // 반려동물 나이

    private String size; // 반려동물 사이즈
    private String petName; // 반려동물 이름
    private String petCharacter; // 반려동물 특징
    private String furColor; // 반려동물 털색
    private String breed; // 반려동물 품종
    private String lastLocation; // 마지막 확인 장소
    private String lastCheckTime; // 확인 시기
    private String contact; // 연락처

    private LocalDateTime createdAt; // 게시글 작성 시간



    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
