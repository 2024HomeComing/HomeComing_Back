package joljak.homecoming.service;

import joljak.homecoming.dto.KakaoResDto;
import joljak.homecoming.dto.ProfileUpdateDto;
import joljak.homecoming.entity.User;
import joljak.homecoming.jwt.JwtUtil;
import joljak.homecoming.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private JwtUtil jwtUtil;

    public Optional<User> getUserProfile(String userId) {
        return userRepository.findByProviderId(userId);
    }

    //프로필 수정
    public User updateUser(ProfileUpdateDto profileUpdateDto, MultipartFile imageFile) throws IOException {
        User user = userRepository.findByProviderId(profileUpdateDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User with ID " + profileUpdateDto.getUserId() + " not found"));

        user.setName(profileUpdateDto.getNickname());
        user.setRegion(profileUpdateDto.getRegion());
        user.setDetails(profileUpdateDto.getDetails());

        // 조건문 내부 디버깅 정보 출력
        System.out.println("이미지 파일: " + (imageFile != null ? imageFile.getOriginalFilename() : "null"));

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String key = "profileimages/" + user.getProviderId() + "/" + imageFile.getOriginalFilename();
                String imageUrl = s3Service.uploadFile(key, imageFile.getBytes());

                // 업로드된 이미지 URL 로그 출력
                System.out.println("S3 업로드 URL: " + imageUrl);

                user.setProfileImage(imageUrl);
            } catch (Exception e) {
                // 예외 발생 시 로그 출력
                System.err.println("이미지 업로드 중 오류 발생: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // 최종 사용자 객체 상태 로그 출력
        System.out.println("저장 전 사용자 객체: " + user);

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
    String providerId=kakaoResDto.getUserId();
    Optional<User> existingUser = userRepository.findByProviderId(providerId);
    String provider = "kakao";
    if (existingUser.isPresent()) {
        // 기존 사용자 업데이트
        User user = existingUser.get();
        user.setName(kakaoResDto.getName());
        user.setEmail(kakaoResDto.getEmail());
        user.setPhoneNumber(kakaoResDto.getPhoneNumber());
        user.setProviderId(kakaoResDto.getUserId());
        user.setFcmToken(kakaoResDto.getFcmToken());
        System.out.println("기존 유저입니다.");
        userRepository.save(user);
    } else {
        // 새로운 사용자 생성
        User newUser = new User();
        newUser.setProvider(provider);
        newUser.setName(kakaoResDto.getName());
        newUser.setEmail(kakaoResDto.getEmail());
        newUser.setPhoneNumber(kakaoResDto.getPhoneNumber());
        newUser.setProviderId(kakaoResDto.getUserId());
        newUser.setFcmToken(kakaoResDto.getFcmToken());
        System.out.println("신규 유저입니다.");
        userRepository.save(newUser);
    }
}
    public String getFcmTokenByUserId(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .map(User::getFcmToken)
                .orElse(null);
    }

    public String getProviderIdByUserId(String userId) {
        Long id = Long.valueOf(userId); // userId를 Long으로 변환
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다.")); // 사용자 없을 시 예외 처리
        return user.getProviderId(); // providerId 반환
    }
}
