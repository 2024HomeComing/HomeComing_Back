package joljak.homecoming.controller;

import joljak.homecoming.entity.PetInfo;
import joljak.homecoming.repository.PetInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PetInfoController {

    @Autowired
    private PetInfoRepository petInfoRepository;

    @GetMapping("/petinfo/{id}")
    public String petInfoPage(@PathVariable Long id, Model model) {
        PetInfo petInfo = petInfoRepository.findById(id).orElseThrow();
        model.addAttribute("petinfo", petInfo);
        return "PetInfo"; // Thymeleaf 템플릿의 이름
    }
}
