package joljak.homecoming.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;
    private String providerId;
    private String name; //프로필전용
    private String email;
    private String phoneNumber;
    private String profileImage; //프로필전용
    private String details; // 프로필전용
    private String region; //프로필전용
    private String fcmToken; // FCM 토큰 저장

}
