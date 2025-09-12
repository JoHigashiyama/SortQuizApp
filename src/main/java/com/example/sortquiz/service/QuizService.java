package com.example.sortquiz.service;

import com.example.sortquiz.entity.Quiz;
import com.example.sortquiz.repository.QuizRepository;
import com.example.sortquiz.viewmodel.AnswerViewModel;
import com.example.sortquiz.viewmodel.QuizDetailViewModel;
import com.example.sortquiz.viewmodel.QuizViewModel;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private int partitionSize = 4;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getQuizlist() {
        return quizRepository.getQuizlist();
    }

//    並び替え前のクイズを分割して、年代順に並び変える
    public List<List<Long>> getSortedCorrectQuizzes(List<Long> quizList) {
        List<List<Long>> sortedCorrectQuizzes = new ArrayList<>();
//        クイズを4個ずつに分割する
        List<List<Long>> quizzes = IntStream.range(0, quizList.size())
                .boxed()
                .collect(Collectors.groupingBy(e -> e / partitionSize, LinkedHashMap::new,
                        Collectors.mapping(quizList::get, Collectors.toList())))
                .values()
                .stream()
                .toList();

        for (int i = 0; i < quizzes.size(); i++) {
//            年代順に並び変えられたクイズ
            List<Long> correctItem = new ArrayList<>();
//            並び変えられる前のクイズ
            List<Long> quizItem = quizzes.get(i);
//            1: quizId, 2: happenYear
            long[][] details = new long[partitionSize][2];
//            情報を取得する
            for (int j = 0; j < partitionSize; j++) {
                details[j][0] = quizItem.get(j);
                details[j][1]  = quizRepository.getDetailsByQuizId(quizItem.get(j)).getHappenYear();
            }
//            年代順に並び変える
            for (int j = 0; j < partitionSize - 1; j++) {
                for (int k = j + 1; k < partitionSize; k++) {
                    if (details[j][1] > details[k][1]) {
                        long quizId = details[j][0];
                        long happenYear = details[j][1];
                        details[j][0] = details[k][0];
                        details[j][1] = details[k][1];
                        details[k][0] = quizId;
                        details[k][1] = happenYear;
                    }
                }
            }
//            並び変えられたクイズのquizIdを保存する
            for (int j = 0; j < partitionSize; j++) {
                correctItem.add(details[j][0]);
            }
            sortedCorrectQuizzes.add(correctItem);
        }
        return sortedCorrectQuizzes;
    }

//    並び替え前と後のクイズを比較し、booleanで返す
    public List<Boolean> compareQuiz(List<List<Long>> sortedQuizzes, List<List<Long>> quizList) {
        List<Boolean> results = new ArrayList<>();
//        並び替え前のクイズを4つずつに分割する
//        クイズを一問ずつ取り出す
        for (int i = 0; i < sortedQuizzes.size(); i++) {
            List<Long> userAnswer = sortedQuizzes.get(i);
            List<Long> correctAnswer = quizList.get(i);
            boolean isCorrect = true;

            for (int j = 0; j < userAnswer.size(); j++) {
//                System.out.println("userAnswer: " + userAnswer);
//                System.out.println("correctAnswer: " + correctAnswer);
                if (!userAnswer.get(j).equals(correctAnswer.get(j))) {
                    isCorrect = false;
                    break;
                }
            }
            results.add(isCorrect);
        }
        return results;
    }

//    解説を取得する
    public List<AnswerViewModel> getQuizDetails(List<List<Long>> sortedQuizzes, List<List<Long>> quizList, List<Boolean> results) {
        List<AnswerViewModel> answers = new ArrayList<>();


        for (int i = 0; i < sortedQuizzes.size(); i++) {
            List<Long> userAnswer = sortedQuizzes.get(i);
            List<Long> correctAnswer = quizList.get(i);
            AnswerViewModel result = new AnswerViewModel();

//            問題番号
            result.setQuizNumber(i+1);
//            正解不正解
            result.setResult(results.get(i));

//            回答・正答・解説
            List<String> userAns = new ArrayList<>();
            List<String> correctAns = new ArrayList<>();
            List<QuizDetailViewModel> details = new ArrayList<>();
            for (int j = 0; j < userAnswer.size(); j++) {
                userAns.add(quizRepository.getDetailsByQuizId(userAnswer.get(j)).getContent());
                QuizDetailViewModel detail = quizRepository.getDetailsByQuizId(correctAnswer.get(j));
                correctAns.add(detail.getContent());
                details.add(detail);
//                System.out.print("select: "+detail.getContent()+", year: "+detail.getHappenYear());
            }
            result.setAnswers(userAns);
            result.setCorrects(correctAns);
            result.setQuizDetails(details);
//            返すリストに格納
            answers.add(result);
        }
        return answers;
    }

    public List<Quiz> getAllQuizzesSortHappenYear() {
        return quizRepository.getAllQuizzesSortHappenYear();
    }

    public List<Quiz> getQuizzesByKeywordAndYear(String keyword, long yearMin, long yearMax) {
        return quizRepository.getQuizzesByKeywordAndYear(keyword, yearMin, yearMax);
    }
}
