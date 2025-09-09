package com.example.sortquiz.service;

import com.example.sortquiz.entity.Quiz;
import com.example.sortquiz.repository.QuizRepository;
import com.example.sortquiz.viewmodel.QuizDetailViewModel;
import com.example.sortquiz.viewmodel.QuizViewModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getQuizlist() {
        return quizRepository.getQuizlist();
    }

//    並び替え前と後のクイズを比較し、正解数を返す
    public long compareQuiz(QuizViewModel[] sortedQuizzes, ArrayList<Long> quizList, long partitionSize) {
        long correctAnswers = 0;
//        並び替え前のクイズを4つずつに分割する
        Collection<List<Long>> collection = quizList
                .stream()
                .collect(Collectors.groupingBy(e -> (e - 1) / partitionSize))
                .values();
        List<List<Long>> quizzes = new ArrayList<>(collection);
//        クイズを一問ずつ取り出す
        for (QuizViewModel quizItem : sortedQuizzes) {
//            選択肢を一個ずつ取り出す
            for (QuizDetailViewModel sortedQuiz : quizItem.getChoices()) {
                if (sortedQuiz.getId() != quizzes
                        .get(Arrays.asList(sortedQuiz).indexOf(quizItem))
                        .get(Arrays.asList(quizItem.getChoices()).indexOf(sortedQuiz))) {
                    break;
                }
            }
            correctAnswers++;
        }


        return correctAnswers;
    }
}
