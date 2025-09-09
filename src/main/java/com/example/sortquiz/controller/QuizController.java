package com.example.sortquiz.controller;


import com.example.sortquiz.entity.Quiz;
import com.example.sortquiz.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public String quizzes(Model model) {
        List<Quiz> quizzes = quizService.getQuizlist();

        List<Map<String, Object>> quizMaps = new ArrayList<>();
        for(int i = 0; i< quizzes.size(); i+= 4){
            Map<String, Object> map = new HashMap<>();
            map.put("quizId", (i/4) + 1);
            List<Map<String,Object>> choices = new ArrayList<>();

            for(int j = i; j < i + 4 && j < quizzes.size(); j++){
                Quiz q = quizzes.get(j);
                choices.add(Map.of(
                        "id",q.getQuizId(),
                        "select",q.getContent(),
                        "year",q.getHappenYear()
                ));
            };

            map.put("choices", choices);
            quizMaps.add(map);
        }

        model.addAttribute("quizzes", quizMaps);
        return "quiz/quiz-game";
    }
}
