package com.example.sortquiz.controller;


import com.example.sortquiz.entity.Quiz;
import com.example.sortquiz.entity.User;
import com.example.sortquiz.form.QuizForm;
import com.example.sortquiz.security.CustomUserDetails;
import com.example.sortquiz.service.QuizService;
import com.example.sortquiz.service.ScoreService;
import com.example.sortquiz.service.UserService;
import com.example.sortquiz.viewmodel.AnswerViewModel;
import com.example.sortquiz.viewmodel.QuizDetailViewModel;
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
        List<Map<String, Object>> quizMaps = new ArrayList<>();
        List<Quiz> quizzes;
//        同一問題内に年代が重複した場合は分け直す
        while (true) {
            quizzes = quizService.getQuizlist();
            quizMaps.clear();
//            年代の重複フラグ
            boolean isDuplicated = false;
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
                    if (j != i) {
//                        年代が重複した場合
                        for (int k = j - 1; k >= i; k--) {
                            if (q.getHappenYear() == quizzes.get(k).getHappenYear()) {
                                isDuplicated = true;
                                break;
                            }
                        }
                    }
                };
                if (isDuplicated) break;
                map.put("choices", choices);
                quizMaps.add(map);
            }
            if (!isDuplicated) break;
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

    @GetMapping("/result")
    public String showResult(@AuthenticationPrincipal CustomUserDetails userDetails,
                             HttpSession httpSession,
                             Model model) {
//        セッションからクイズ結果を取り出す
        model.addAttribute("correctCount", httpSession.getAttribute("correctCount"));
        model.addAttribute("score", httpSession.getAttribute("score"));
        model.addAttribute("time", httpSession.getAttribute("time"));
        List<AnswerViewModel> quizDetails = (List<AnswerViewModel>) httpSession.getAttribute("quizResults");
        model.addAttribute("quizDetails", quizDetails);
//        セッション破棄
        httpSession.removeAttribute("correctCount");
        httpSession.removeAttribute("score");
        httpSession.removeAttribute("time");
        httpSession.removeAttribute("quizResults");
        return "quiz/quiz-result";
    }

    @GetMapping("/failed")
    public String showGameOver() {
        return "quiz/quiz-gameover";
    }

    @GetMapping("/history")
    public String showHistory(Model model) {
        List<Quiz> histories = quizService.getAllQuizzesSortHappenYear();
        model.addAttribute("histories", histories);
        return "quiz/quiz-history";
    }

    @GetMapping("/giveUp")
    public String giveUp(HttpSession httpSession) {
        httpSession.removeAttribute("quizList");
        return "redirect:/quiz";
    }
}
