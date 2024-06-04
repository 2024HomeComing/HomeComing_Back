package joljak.homecoming.controller;

import joljak.homecoming.entity.PetInfo;
import joljak.homecoming.entity.Report;
import joljak.homecoming.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    @Autowired
    private PetService petService;

    @GetMapping("/{userId}")
    public List<PetInfo> getPetsByUserId(@PathVariable Long userId) {
        return petService.getPetsByUserId(userId);
    }

    @GetMapping("/reports/{petInfoId}")
    public List<Report> getReportsByPetInfoId(@PathVariable Long petInfoId) {
        return petService.getReportsByPetInfoId(petInfoId);
    }
}
