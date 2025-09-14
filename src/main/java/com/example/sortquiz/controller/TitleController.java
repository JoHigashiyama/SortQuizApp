package com.example.sortquiz.controller;

import com.example.sortquiz.entity.Title;
import com.example.sortquiz.entity.UserTitle;
import com.example.sortquiz.security.CustomUserDetails;
import com.example.sortquiz.service.TitleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/title")
public class TitleController {
    private final TitleService titleService;

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }


    @GetMapping()
    public String scores(@AuthenticationPrincipal CustomUserDetails userDetails, Model model ) {
        List<Title> titles = titleService.getTitles();
        List<UserTitle> userTitles = titleService.getUserTitles(userDetails.getUserId());

        model.addAttribute("titles",titles);
        model.addAttribute("userTitles",userTitles);
        return "title/titles";
    }
}
