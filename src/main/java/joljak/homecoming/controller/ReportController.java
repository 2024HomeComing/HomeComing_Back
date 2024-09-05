package joljak.homecoming.controller;

import joljak.homecoming.dto.FcmMessageRequestDto;
import joljak.homecoming.entity.PetInfo;
import joljak.homecoming.entity.Report;
import joljak.homecoming.repository.PetInfoRepository;
import joljak.homecoming.repository.ReportRepository;
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
        String userId = String.valueOf(petInfo.getUser().getId());
        String fcmToken = userService.getFcmTokenByUserId(userId);

        if (fcmToken != null) {
            // 신고 접수 알림 전송
            FcmMessageRequestDto fcmMessageRequest = new FcmMessageRequestDto(
                    "신고가 접수되었습니다",
                    "당신의 반려동물에 대한 신고가 접수되었습니다.",
                    fcmToken
            );
            fcmService.sendNotification(fcmMessageRequest);
        }

        return new ModelAndView("redirect:/successPage") ;
    }

    @GetMapping("/successPage")
    public String successReport(){
        return "successPage";
    }


}
