package com.example.sortquiz.controller;

import com.example.sortquiz.response.QuizResponse;
import com.example.sortquiz.security.CustomUserDetails;
import com.example.sortquiz.service.QuizService;
import com.example.sortquiz.service.ScoreService;
import com.example.sortquiz.service.UserService;
import com.example.sortquiz.viewmodel.AnswerViewModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizRestController {
    private final QuizService quizService;
    private final UserService userService;
    private final ScoreService scoreService;

    public QuizRestController(QuizService quizService, UserService userService, ScoreService scoreService) {
        this.quizService = quizService;
        this.userService = userService;
        this.scoreService = scoreService;
    }
    @PostMapping("/finish")
    public ResponseEntity<QuizResponse> createScore(@RequestParam("answers") List<List<Long>> answers,
                                                    @RequestParam("timeLeft") long time,
                                                    @AuthenticationPrincipal CustomUserDetails userDetails,
                                                    HttpSession httpSession) {
        QuizResponse quizResponse = new QuizResponse();

//      セッションから並び替え前のクイズを取り出す
        ArrayList<Long> quizList = (ArrayList<Long>) httpSession.getAttribute("quizList");
//        並び替え前と後を比較して、問題の正解・不正解を取得する
        List<Boolean> results = quizService.compareQuiz(answers, quizList);
//        点数を取得する
        long score = scoreService.calculateScore(results.stream().filter(result-> result).count(), time);
//        問題ごとのデータ(回答・正答・解説)
        List<AnswerViewModel> quizResults = quizService.getQuizDetails(answers, quizList, results);
//        セッションにデータを保存する
        httpSession.setAttribute("correctCount", results.stream().filter(result-> result).count());
        httpSession.setAttribute("score", score);
        httpSession.setAttribute("time", time);
        httpSession.setAttribute("quizResults", quizResults);

//        リダイレクト先のurlを格納する
        quizResponse.setRedirectUrl("/quiz/result");
        return ResponseEntity.ok().body(quizResponse);
    }


}
