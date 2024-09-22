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

        String providerId = petInfoDTO.getUserId();
        User user = userRepository.findByProviderId(providerId)
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
        petInfo.setQrCodeImage(qrCode);
        petInfoRepository.save(petInfo);
        System.out.println("QR DB에 저장됨.");
        System.out.println("QR 생성됨");
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.IMAGE_PNG).body(qrCode);
    }

    // QR 코드 수정
    @PutMapping("/update/{petId}")
    public ResponseEntity<?> updatePetInfo(@PathVariable Long petId, @RequestBody PetInfoDTO petInfoDTO) throws Exception {

        PetInfo petInfo = petInfoRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet info not found"));

        String providerId = petInfoDTO.getUserId();
        User user = userRepository.findByProviderId(providerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // PetInfo 정보 수정
        petInfo.setName(petInfoDTO.getName());
        petInfo.setSpecies(petInfoDTO.getSpecies());
        petInfo.setHairColor(petInfoDTO.getHairColor());
        petInfo.setLikeDislike(petInfoDTO.getLikeDislike());
        petInfo.setLocation(petInfoDTO.getLocation());
        petInfo.setPhoneNumber(petInfoDTO.getPhoneNumber());
        petInfo.setManual(petInfoDTO.getManual());
        petInfo.setUser(user);

        // QR 코드 URL을 업데이트할 경우 새로 생성
        String petQRInfoUrl = "https://homeskyul.store/petinfo/" + petInfo.getId();
        byte[] qrCode = qrCodeService.generateQRCode(petQRInfoUrl, 300, 300);
        petInfo.setQrCodeImage(qrCode);

        petInfoRepository.save(petInfo);
        System.out.println("QR 코드 및 펫 정보 수정됨.");

        return new ResponseEntity<>(petInfo, HttpStatus.OK);
    }
}
