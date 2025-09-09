package com.example.sortquiz.viewmodel;

import lombok.Data;

import java.util.List;

@Data
public class AnswerViewModel {
    private long quizNumber;
    private boolean result;
    private List<String> answerQuizzes;
    private List<String> correctQuizzes;
    private List<QuizDetailViewModel> quizDetails;
}
