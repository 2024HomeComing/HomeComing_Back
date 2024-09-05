package joljak.homecoming.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateDto {

    private String userId;

    private String nickname;    // 닉네임
    private String region;  // 거주지역
    private String details; //소개글

}
