package joljak.homecoming.controller;

import joljak.homecoming.dto.PetInfoDTO;
import joljak.homecoming.entity.PetInfo;
import joljak.homecoming.entity.User;
import joljak.homecoming.repository.PetInfoRepository;
import joljak.homecoming.repository.UserRepository;
import joljak.homecoming.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/qr")
public class QRCodeController {

    @Autowired
    private PetInfoRepository petInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping("/generate")

    public ResponseEntity<byte[]> createPetInfo(@RequestBody PetInfoDTO petInfoDTO) throws Exception {

        String provider = "kakao"  + petInfoDTO.getUserId();
        User user = userRepository.findByProvider(provider)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // PetInfo 객체 생성
        PetInfo petInfo = new PetInfo();
        petInfo.setName(petInfoDTO.getName());
        petInfo.setSpecies(petInfoDTO.getSpecies());
        petInfo.setHairColor(petInfoDTO.getHairColor());
        petInfo.setLikeDislike(petInfoDTO.getLikeDislike());
        petInfo.setLocation(petInfoDTO.getLocation());
        petInfo.setPhoneNumber(petInfoDTO.getPhoneNumber());
        petInfo.setManual(petInfoDTO.getManual());
        petInfo.setUser(user);

        petInfo = petInfoRepository.save(petInfo);
        String petQRInfoUrl = "https://homeskyul.store/petinfo/" + petInfo.getId(); // 실제 프론트엔드 URL로 교체해야 합니다.
        byte[] qrCode = qrCodeService.generateQRCode(petQRInfoUrl, 300, 300);
            System.out.println("QR 생성됨");
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.IMAGE_PNG).body(qrCode);
    }
}
