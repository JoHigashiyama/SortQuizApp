package com.example.sortquiz.service;

import com.example.sortquiz.entity.Quiz;
import com.example.sortquiz.repository.QuizRepository;
import com.example.sortquiz.viewmodel.AnswerViewModel;
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
    private long partitionSize = 4;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getQuizlist() {
        return quizRepository.getQuizlist();
    }

//    並び替え前と後のクイズを比較し、正解数を返す
    public List<Boolean> compareQuiz(List<List<Long>> sortedQuizzes, ArrayList<Long> quizList) {
        List<Boolean> results = new ArrayList<>();
//        並び替え前のクイズを4つずつに分割する
        Collection<List<Long>> collection = quizList
                .stream()
                .collect(Collectors.groupingBy(e -> (e - 1) / partitionSize))
                .values();
        List<List<Long>> quizzes = new ArrayList<>(collection);

//        クイズを一問ずつ取り出す
        for (List<Long> quizItem : sortedQuizzes) {
//            選択肢を一個ずつ取り出す
            for (long sortedQuizId : quizItem) {
                if (sortedQuizId != quizzes
                        .get(Arrays.asList(sortedQuizzes).indexOf(quizItem))
                        .get(Arrays.asList(quizItem).indexOf(sortedQuizId))) {
                    results.add(false);
                    break;
                }
            }
            results.add(true);
        }
        return results;
    }

//    解説を取得する
    public List<AnswerViewModel> getQuizDetails(List<List<Long>> sortedQuizzes, ArrayList<Long> quizList, List<Boolean> result) {
        List<AnswerViewModel> answers = new ArrayList<>();

//        並び替え前のクイズを分割する
        Collection<List<Long>> collection = quizList
                .stream()
                .collect(Collectors.groupingBy(e -> (e - 1) / partitionSize))
                .values();
        List<List<Long>> quizzes = new ArrayList<>(collection);

//          クイズを一問ずつ取り出す
        for (List<Long> quizItem : sortedQuizzes) {
            AnswerViewModel answerItem = new AnswerViewModel();
//            問題数（何問目か）
            answerItem.setQuizNumber(Arrays.asList(sortedQuizzes).indexOf(quizItem) + 1);
//            正解不正解
            answerItem.setResult(result.get(Arrays.asList(sortedQuizzes).indexOf(quizItem)));
//            解答/正答/解説
            List<String> playerAnswer = new ArrayList<>();
            List<String> correctAnswer = new ArrayList<>();
            List<QuizDetailViewModel> details = new ArrayList<>();
//            選択肢を一個ずつ取り出す
            for (long sortedQuizId : quizItem) {
//                解答を格納する
                playerAnswer.add(quizRepository.getDetailsByQuizId(sortedQuizId).getSelect());
//                正答のクイズIDから選択肢のレコードを取得する
                QuizDetailViewModel detail = quizRepository.getDetailsByQuizId(quizzes
                        .get(Arrays.asList(sortedQuizzes).indexOf(quizItem))
                        .get(Arrays.asList(quizItem).indexOf(sortedQuizId)));
//                正答を格納する
                correctAnswer.add(detail.getSelect());
//                選択肢の詳細を格納する
                details.add(detail);
            }
//            回答/正答/解説の格納
            answerItem.setAnswers(playerAnswer);
            answerItem.setCorrects(correctAnswer);
            answerItem.setQuizDetails(details);
//            返り値のリストに格納
            answers.add(answerItem);
        }
        return answers;
    }
}
