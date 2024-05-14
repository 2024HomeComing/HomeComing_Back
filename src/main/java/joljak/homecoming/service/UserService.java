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

    public String createUserAndIssueToken(KakaoResDto kakaoResDto) {
        User user = new User();
        user.setProvider(kakaoResDto.getId());
        user.setName(kakaoResDto.getName());
        user.setEmail(kakaoResDto.getEmail());
        user.setPhonenumber(kakaoResDto.getPhonenumber());
        userRepository.save(user);

        String provider = kakaoResDto.getId();
        String name = kakaoResDto.getName();

        //JWT 토큰 생성

        return jwtUtil.createJwt(provider, name, 60*60*10L);

    }
}
