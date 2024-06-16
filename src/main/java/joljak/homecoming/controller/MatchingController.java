package joljak.homecoming.controller;

import joljak.homecoming.dto.MatchResultDto;
import joljak.homecoming.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/match")
public class MatchingController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/{boardId}")
    public MatchResultDto findBestMatch(@PathVariable Long boardId) {
        return matchService.findBestMatch(boardId);
    }
}
