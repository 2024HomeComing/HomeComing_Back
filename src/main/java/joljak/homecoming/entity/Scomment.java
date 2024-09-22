package joljak.homecoming.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Scomment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String writer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "sightingboard_id")
    private SightingBoard sightingBoard;

    private LocalDateTime time; // 생성 시간

    @PrePersist
    public void prePersist() {
        this.time = LocalDateTime.now();
    }
}
