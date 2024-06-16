package joljak.homecoming.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SightingBoardDto {

    String wtitle;
    String wbreed; // 품종
    String wsize; // 크기
    String wcolor; // 털 색상
    String wcharacteristics; // 특징
    String wlastSeenLocation; // 위치
    String wlastSeenTime; // 시간
    String wcontact; // 연락처
    String wadditionalInfo;
    String userId;
    String imageUrl;
}

