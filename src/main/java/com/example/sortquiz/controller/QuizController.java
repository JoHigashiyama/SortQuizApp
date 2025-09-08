package com.example.sortquiz.controller;


import com.example.sortquiz.entity.Quiz;
import com.example.sortquiz.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        List<Map<String, Object>> quizMaps = quizzes.stream()
                .map(quiz -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", quiz.getQuizId());
                    map.put("select", quiz.getContent());
                    map.put("year", quiz.getHappenYear());
                    return map;
                })
                .toList();

        model.addAttribute("quizzes", quizMaps);
        return "quiz/quiz-list";
    }
}
