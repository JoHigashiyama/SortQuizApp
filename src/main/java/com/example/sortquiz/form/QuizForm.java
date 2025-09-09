package com.example.sortquiz.form;

import com.example.sortquiz.viewmodel.QuizViewModel;
import lombok.Data;

import java.util.List;

@Data
public class QuizForm {
    private long time;
    private QuizViewModel[] sortedQuizzes;
}
