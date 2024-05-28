package joljak.homecoming.service;

import joljak.homecoming.dto.KakaoResDto;
import joljak.homecoming.entity.User;
import joljak.homecoming.jwt.JwtUtil;
import joljak.homecoming.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    //프로필 수정
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDetails.getName());
        return userRepository.save(user);
    }

    //사용자 정보 저장후 토큰발급(로그인)

//    public String createUserAndIssueToken(KakaoResDto kakaoResDto) {
//        User user = new User();
//        user.setProvider(kakaoResDto.getId());
//        user.setName(kakaoResDto.getName());
//        user.setEmail(kakaoResDto.getEmail());
//        user.setPhoneNumber(kakaoResDto.getPhonenumber());
//        userRepository.save(user);
//
////        String provider = kakaoResDto.getId();
////        String name = kakaoResDto.getName();
////
////        //JWT 토큰 생성
////
////        return jwtUtil.createJwt(provider, name, 60*60*10L);
//
//    }

    //카카오 로그인
public void createUser(KakaoResDto kakaoResDto) {
    // 사용자 식별자를 기준으로 기존 사용자 검색
    String provider = "kakao" + kakaoResDto.getUserId();
    Optional<User> existingUser = userRepository.findByProvider(provider);

    if (existingUser.isPresent()) {
        // 기존 사용자 업데이트
        User user = existingUser.get();
        user.setName(kakaoResDto.getName());
        user.setEmail(kakaoResDto.getEmail());
        user.setPhoneNumber(kakaoResDto.getPhoneNumber());
        System.out.println("기존 유저입니다.");
        userRepository.save(user);
    } else {
        // 새로운 사용자 생성
        User newUser = new User();
        newUser.setProvider(provider);
        newUser.setName(kakaoResDto.getName());
        newUser.setEmail(kakaoResDto.getEmail());
        newUser.setPhoneNumber(kakaoResDto.getPhoneNumber());
        System.out.println("신규 유저입니다.");
        userRepository.save(newUser);
    }
}
}
