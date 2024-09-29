package joljak.homecoming.controller;

import joljak.homecoming.dto.PetInfoDTO;
import joljak.homecoming.entity.PetInfo;
import joljak.homecoming.entity.User;
import joljak.homecoming.repository.PetInfoRepository;
import joljak.homecoming.repository.UserRepository;
import joljak.homecoming.service.QRCodeService;
import joljak.homecoming.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private S3Service s3Service;


    @PostMapping("/generate")
    public ResponseEntity<byte[]> createPetInfo(
            @RequestPart("petInfo") PetInfoDTO petInfoDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws Exception {


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

        // 첨부된 이미지 파일을 S3에 업로드하고 URL을 저장
        if (!imageFile.isEmpty()) {
            String key = "petInfo/" + petInfo.getUser().getProviderId() + "/" + imageFile.getOriginalFilename();
            String imageUrl = s3Service.uploadFile(key, imageFile.getBytes());
            petInfo.setImageUrl(imageUrl); // 이미지 URL을 DB에 저장
        }
        petInfo = petInfoRepository.save(petInfo);

        String petQRInfoUrl = "https://homeskyul.store/petinfo/" + petInfo.getId(); // 실제 프론트엔드 URL로 교체해야 합니다.
        byte[] qrCode = qrCodeService.generateQRCode(petQRInfoUrl, 300, 300);
        petInfo.setQrCodeImage(qrCode);
        petInfoRepository.save(petInfo);

        System.out.println("QR 및 이미지가 S3에 저장되고 DB에 URL 저장됨.");
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.IMAGE_PNG).body(qrCode);
    }

    // QR 코드 수정
    // QR 코드 및 사진 수정
    @PutMapping("/update/{petId}")
    public ResponseEntity<?> updatePetInfo(
            @PathVariable Long petId,
            @RequestPart("petInfo") PetInfoDTO petInfoDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws Exception {

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

        // 첨부된 이미지 파일을 S3에 업로드하고 URL을 저장
        if (!imageFile.isEmpty()) {
            String key = "petInfo/" + petInfo.getUser().getProviderId() + "/" + imageFile.getOriginalFilename();
            String imageUrl = s3Service.uploadFile(key, imageFile.getBytes());
            petInfo.setImageUrl(imageUrl); // 이미지 URL을 DB에 저장
        }
        petInfo = petInfoRepository.save(petInfo);

        // QR 코드 URL을 업데이트할 경우 새로 생성
        String petQRInfoUrl = "https://homeskyul.store/petinfo/" + petInfo.getId();
        byte[] qrCode = qrCodeService.generateQRCode(petQRInfoUrl, 300, 300);
        petInfo.setQrCodeImage(qrCode);
        petInfoRepository.save(petInfo);

        petInfoRepository.save(petInfo);
        System.out.println("QR 코드 및 펫 정보 수정됨.");

        return new ResponseEntity<>(petInfo, HttpStatus.OK);
    }
}
