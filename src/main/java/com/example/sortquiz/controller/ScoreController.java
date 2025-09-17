package com.example.sortquiz.controller;

import com.example.sortquiz.entity.Score;
import com.example.sortquiz.security.CustomUserDetails;
import com.example.sortquiz.service.ScoreService;
import com.example.sortquiz.viewmodel.ScoreHistoryViewModel;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("/history/sort")
//    @ResponseBody
//    public String getHistories(@RequestParam(required = false) String sort,Model model,@AuthenticationPrincipal CustomUserDetails userDetails) {
//        List<ScoreHistoryViewModel> history;
//
//        if ("created_at".equals(sort)) {
//            history = scoreService.selectScoresByUserId(userDetails.getUserId());
//        }else{
//            history = scoreService.selectScoresScoreByUserId(userDetails.getUserId());
//        }
//
//        model.addAttribute("history", history);
//
//        return "score/score-history";
//    }

    @GetMapping("/history/sort")
    @ResponseBody
    public List<ScoreHistoryViewModel> getHistories(
            @RequestParam(required = false) String sort,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if ("created_at".equals(sort)) {
            return scoreService.selectScoresByUserId(userDetails.getUserId());
        } else {
            return scoreService.selectScoresScoreByUserId(userDetails.getUserId());
        }
    }
}
