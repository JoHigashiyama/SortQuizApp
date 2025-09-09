package com.example.sortquiz.viewmodel;

import lombok.Data;

import java.util.List;

@Data
public class QuizViewModel {
    private long quizId;
    private QuizDetailViewModel[] choices;
}
