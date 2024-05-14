package joljak.homecoming.controller;

import joljak.homecoming.dto.KakaoResDto;
import joljak.homecoming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class KakaoController {

    @Autowired
    private UserService userService;

    @PostMapping("/kakao")
    public ResponseEntity<?> loginUser(@RequestBody KakaoResDto kakaoResDto) {
        String token = userService.createUserAndIssueToken(kakaoResDto);

        System.out.println("사용자 정보 저장됨. 카카오 ID: " + kakaoResDto.getId());
        System.out.println("토큰 발급됨. 토큰: " + token);
        // HttpHeaders 객체 생성
        HttpHeaders headers = new HttpHeaders();
        // "Authorization" 헤더에 "Bearer " + token 형식으로 토큰 추가
        headers.add("Authorization", "Bearer " + token);

        // ResponseEntity 반환 시 헤더 포함
        return new ResponseEntity<>(null, headers, HttpStatus.OK);
    }
}
