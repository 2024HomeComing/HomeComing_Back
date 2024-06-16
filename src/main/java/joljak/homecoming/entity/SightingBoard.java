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

    private String wtitle;
    private String username;
    private String wbreed; // 품종
    private String wsize; // 크기
    private String wcolor; // 털 색상
    private String wcharacteristics; // 특징
    private String wadditionalInfo;
    private String wlastSeenLocation; // 위치
    private String wlastSeenTime; // 시간
    private String wcontact; // 연락처

    private LocalDateTime wcreatedAt; // 게시글 작성 시간
    private String wImageUrl;

    @ManyToOne
    private User user;
    @PrePersist
    public void prePersist() {
        this.wcreatedAt = LocalDateTime.now();
    }
}
