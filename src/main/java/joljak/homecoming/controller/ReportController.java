package joljak.homecoming.controller;

import joljak.homecoming.dto.FcmMessageRequestDto;
import joljak.homecoming.entity.PetInfo;
import joljak.homecoming.entity.Report;
import joljak.homecoming.repository.PetInfoRepository;
import joljak.homecoming.repository.ReportRepository;
import joljak.homecoming.repository.UserRepository;
import joljak.homecoming.service.FirebaseCloudMessageService;
import joljak.homecoming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {

    @Autowired
    private PetInfoRepository petInfoRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private FirebaseCloudMessageService fcmService;

    @Autowired
    private UserService userService;

    @PostMapping("/report")
    public ModelAndView submitReport(@ModelAttribute Report report, @RequestParam("petInfoId") Long petInfoId) {
        //신고된 반려동물 정보 조회
        PetInfo petInfo = petInfoRepository.findById(petInfoId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid PetInfo ID: " + petInfoId));
        report.setPetInfo(petInfo);
        Report savedReport = reportRepository.save(report);

        // 반려동물 주인의 FCM 토큰 조회
        String uId = String.valueOf(petInfo.getUser().getId());
        System.out.println("유저: " + uId);
        String fcmToken = userService.getFcmTokenByUserId(uId);
        System.out.println("토큰값: " + fcmToken);
        String userId = userService.getProviderIdByUserId(uId);
        System.out.println("카카오 고유 ID: " + userId);
        if (fcmToken != null) {
            String petName = petInfo.getName(); // petInfo 객체에서 이름을 가져옴
            // 신고 접수 알림 전송
            FcmMessageRequestDto fcmMessageRequest = new FcmMessageRequestDto(
                    "신고가 접수되었습니다!",
                    petName + "에 대한 신고가 접수되었습니다!",
                    fcmToken,
                    userId
            );
            fcmService.sendNotification(fcmMessageRequest);
            System.out.println("알람이 성공적으로 발송됨.");
        }

        return new ModelAndView("redirect:/successPage") ;
    }

    @GetMapping("/successPage")
    public String successReport(){
        return "successPage";
    }


}
