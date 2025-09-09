package com.example.sortquiz.controller;

import com.example.sortquiz.service.RankingService;
import com.example.sortquiz.viewModel.UserViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/rank")
public class RankingController {
    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/ranking")
    public String scores(Model model) {
        List<UserViewModel> BestScores = rankingService.getTop10ByBestScore();
        List<UserViewModel> TotalScores = rankingService.getTop10ByTotalScore();

        model.addAttribute("BestScores",BestScores);
        model.addAttribute("TotalScores",TotalScores);
        return "quiz/ranking";
    }
}
