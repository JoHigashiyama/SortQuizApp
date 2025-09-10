package com.example.sortquiz.viewmodel;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AnswerViewModel implements Serializable {
    private long quizNumber;
    private boolean result;
    private List<String> answers;
    private List<String> corrects;
    private List<QuizDetailViewModel> quizDetails;
}
