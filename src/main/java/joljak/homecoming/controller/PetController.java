package joljak.homecoming.controller;

import joljak.homecoming.entity.PetInfo;
import joljak.homecoming.entity.Report;
import joljak.homecoming.entity.User;
import joljak.homecoming.repository.UserRepository;
import joljak.homecoming.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetService petService;

    @GetMapping("/{userId}")
    public List<PetInfo> getPetsByUserId(@PathVariable String userId) {
        User user = userRepository.findByProviderId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long userDbId = user.getId();
        return petService.getPetsByUserId(userDbId);
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
