package joljak.homecoming.controller;

import joljak.homecoming.entity.PetInfo;
import joljak.homecoming.entity.Report;
import joljak.homecoming.entity.User;
import joljak.homecoming.repository.PetInfoRepository;
import joljak.homecoming.repository.UserRepository;
import joljak.homecoming.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private PetInfoRepository petInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetService petService;

    //반려동물 리스트 조회
    @GetMapping("/{userId}")
    public List<PetInfo> getPetsByUserId(@PathVariable String userId) {
        User user = userRepository.findByProviderId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long userDbId = user.getId();
        return petService.getPetsByUserId(userDbId);
    }

    //반려동물 상세조회
    @GetMapping("/{petInfoId}")
    public PetInfo getPetById(@PathVariable Long petInfoId) {
        // 반려동물 ID로 반려동물 정보를 조회

        return petService.getPetById(petInfoId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
    }

    // QR 코드 삭제
    @DeleteMapping("/delete/{petId}")
    public ResponseEntity<?> deletePetInfo(@PathVariable Long petId) {

        PetInfo petInfo = petInfoRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet info not found"));

        petInfoRepository.delete(petInfo);
        System.out.println("QR 코드 및 펫 정보 삭제됨.");

        return new ResponseEntity<>("Pet info and QR code deleted", HttpStatus.OK);
    }

    @GetMapping("/reports/{petInfoId}")
    public List<Report> getReportsByPetInfoId(@PathVariable Long petInfoId) {
        return petService.getReportsByPetInfoId(petInfoId);
    }

    @GetMapping("/report/{reportId}")
    public ResponseEntity<Optional<Report>> getReportById(@PathVariable Long reportId) {
        Optional<Report> report = petService.getReportById(reportId);
        // 만약 신고 정보가 존재하지 않는다면, 404 Not Found 응답을 반환합니다.
        if (report == null) {
            return ResponseEntity.notFound().build();
        }

        // 신고 정보를 포함한 응답을 반환합니다.
        return ResponseEntity.ok(report);
    }
}
