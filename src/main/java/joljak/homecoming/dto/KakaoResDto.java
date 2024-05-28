package joljak.homecoming.dto;

import lombok.Getter;

@Getter
public class KakaoResDto {

    //카카오 SDK 로그인후 앱쪽에서 받은 사용자 정보 저장
    String userId;
    String name;
    String email;
    String phoneNumber;
}
