package com.example.sortquiz.controller;

import com.example.sortquiz.security.CustomUserDetails;
import com.example.sortquiz.service.ScoreService;
import com.example.sortquiz.viewmodel.ScoreHistoryViewModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/score")
public class ScoreController {
    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/history")
    public String showScoreHistory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   Model model) {
        String username = userDetails.getUsername();
        List<ScoreHistoryViewModel> history = scoreService.selectScoresByUserId(userDetails.getUserId());
        model.addAttribute("username", username);
        model.addAttribute("history", history);
        return "score/score-history";
    }
}
