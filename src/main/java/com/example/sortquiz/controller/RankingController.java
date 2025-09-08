package com.example.sortquiz.controller;

import com.example.sortquiz.service.RankingService;
import com.example.sortquiz.viewModel.UserViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RankingController {
    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/ranking")
    public String scores(Model model) {
        List<UserViewModel> scores = rankingService.getTop10ByBestScore();
        model.addAttribute("scores",scores);
        return "quiz/ranking";
    }
}
