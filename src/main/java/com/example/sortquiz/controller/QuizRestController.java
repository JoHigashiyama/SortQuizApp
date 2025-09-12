package com.example.sortquiz.controller;

import com.example.sortquiz.entity.Score;
import com.example.sortquiz.form.QuizForm;
import com.example.sortquiz.response.GameOverResponse;
import com.example.sortquiz.response.QuizResponse;
import com.example.sortquiz.security.CustomUserDetails;
import com.example.sortquiz.service.QuizService;
import com.example.sortquiz.service.ScoreService;
import com.example.sortquiz.service.UserService;
import com.example.sortquiz.viewmodel.AnswerViewModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz/api")
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
    public ResponseEntity<QuizResponse> createScore(@RequestBody QuizForm quizForm,
                                                    @AuthenticationPrincipal CustomUserDetails userDetails,
                                                    HttpSession httpSession) {
        QuizResponse quizResponse = new QuizResponse();
//        制限時間
        long limitTime = 100;
        long answerTime = limitTime - quizForm.getTimeLeft();

//      セッションから並び替え前のクイズを取り出す
        List<Long> quizList = (List<Long>) httpSession.getAttribute("quizList");
//        並び替え前のクイズを分割して、年順に並べ替える
        List<List<Long>> correctAnswer = quizService.getSortedCorrectQuizzes(quizList);
//        並び替え前と後を比較して、問題の正解・不正解を取得する
        List<Boolean> results = quizService.compareQuiz(quizForm.getAnswers(), correctAnswer);
//        点数を取得する
        long scoreCalculated = scoreService.calculateScore(results, quizForm.getTimeLeft());
//        スコア登録
        Score score = new Score();
        score.setUserId(userDetails.getUserId());
        score.setScore(scoreCalculated);
        score.setCorrectCount(results.stream().filter(result->result).count());
        scoreService.createScore(score);
//        ベストスコア、総スコアの更新
        userService.updateUserScoreByUserId(userDetails.getUserId());
//        アチーブメント更新処理


//        問題ごとのデータ(回答・正答・解説)
        List<AnswerViewModel> quizResults = quizService.getQuizDetails(quizForm.getAnswers(), correctAnswer, results);
//        セッションにデータを保存する
        httpSession.setAttribute("correctCount", results.stream().filter(result-> result).count());
        httpSession.setAttribute("score", scoreCalculated);
        httpSession.setAttribute("time", answerTime);
        httpSession.setAttribute("quizResults", quizResults);

//        リダイレクト先のurlを格納する
        quizResponse.setRedirectUrl("result");
        return ResponseEntity.ok().body(quizResponse);
    }
}
