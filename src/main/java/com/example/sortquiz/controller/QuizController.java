package com.example.sortquiz.controller;


import com.example.sortquiz.entity.Quiz;
import com.example.sortquiz.entity.User;
import com.example.sortquiz.form.QuizForm;
import com.example.sortquiz.security.CustomUserDetails;
import com.example.sortquiz.service.QuizService;
import com.example.sortquiz.service.ScoreService;
import com.example.sortquiz.service.UserService;
import com.example.sortquiz.viewmodel.AnswerViewModel;
import com.example.sortquiz.viewmodel.QuizViewModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;
    private final UserService userService;
    private final ScoreService scoreService;

    public QuizController(QuizService quizService, UserService userService, ScoreService scoreService) {
        this.quizService = quizService;
        this.userService = userService;
        this.scoreService = scoreService;
    }

    @GetMapping
    public String top(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User users = userService.getUserInformation(userDetails.getUserId());
        model.addAttribute("user",users);

        return "quiz/quiz-top";
    }

    @GetMapping("/game")
    public String quizzes(Model model, HttpSession httpSession) {
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

        ArrayList<Long> quizList = new ArrayList<>();

        quizzes.stream().map(quiz -> {
            quizList.add(quiz.getQuizId());
            return quizList;
        }).toList();
        httpSession.setAttribute("quizList", quizList);


        model.addAttribute("quizzes", quizMaps);
        return "quiz/quiz-game";
    }

    @PostMapping("/result")
    public String showResult(QuizForm quizForm,
                             @AuthenticationPrincipal CustomUserDetails userDetails,
                             HttpSession httpSession,
                             Model model) {
//        セッションから並び替え前のクイズを取り出す
        ArrayList<Long> quizList = (ArrayList<Long>) httpSession.getAttribute("quizList");

        List<Boolean> results = quizService.compareQuiz(quizForm.getSortedQuizzes(), quizList);
        long point = scoreService.calculateScore(results.stream().filter(result-> result = true).count(), quizForm.getTime());
        long time = quizForm.getTime();

//        問題の詳細を格納する
        List<AnswerViewModel> details = quizService.getQuizDetails(quizForm.getSortedQuizzes(), quizList, results);

        model.addAttribute("correctAnswers", results.stream().filter(result->result = true).count());
        model.addAttribute("point", point);
        model.addAttribute("time", time);
        model.addAttribute("quizDetails", details);
        return "quiz/quiz-result";
    }
}
