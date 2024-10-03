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
        userService.createUser(kakaoResDto);

        System.out.println("사용자 정보 저장됨. 카카오 ID: " + kakaoResDto.getUserId());

        return ResponseEntity.ok().build();
    }
}
