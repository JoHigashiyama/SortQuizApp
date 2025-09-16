package com.example.sortquiz.controller;

import com.example.sortquiz.entity.Title;
import com.example.sortquiz.response.TitleResponse;
import com.example.sortquiz.security.CustomUserDetails;
import com.example.sortquiz.service.TitleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quiz/api")
public class TitleRestController {
    private final TitleService titleService;

    public TitleRestController(TitleService titleService) {
        this.titleService = titleService;
    }

    @GetMapping("/achieveTitle")
    public ResponseEntity<TitleResponse> getAchieveTitle(HttpSession httpSession,
                                                         CustomUserDetails userDetails) {
//        セッションから達成称号を受け取る
        List<Long> achievedTitleId = (List<Long>) httpSession.getAttribute("achievedTitles");
//        titleIdから称号のデータを受け取る
        List<Title> titleList = titleService.getTitlesByTitleId(achievedTitleId);
        TitleResponse response = new TitleResponse();
        response.setAchievedTitles(titleList);
        return ResponseEntity.ok().body(response);
    }
}
