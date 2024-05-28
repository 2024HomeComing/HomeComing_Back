package joljak.homecoming.controller;

import joljak.homecoming.entity.PetInfo;
import joljak.homecoming.entity.Report;
import joljak.homecoming.repository.PetInfoRepository;
import joljak.homecoming.repository.ReportRepository;
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

    @PostMapping("/report")
    public ModelAndView submitReport(@ModelAttribute Report report, @RequestParam("petInfoId") Long petInfoId) {
        PetInfo petInfo = petInfoRepository.findById(petInfoId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid PetInfo ID: " + petInfoId));
        report.setPetInfo(petInfo);
        Report savedReport = reportRepository.save(report);
        return new ModelAndView("redirect:/successPage") ;
    }

    @GetMapping("/successPage")
    public String successReport(){
        return "successPage";
    }
}
