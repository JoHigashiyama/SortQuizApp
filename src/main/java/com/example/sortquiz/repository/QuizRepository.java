package com.example.sortquiz.repository;

import com.example.sortquiz.entity.Quiz;
import com.example.sortquiz.mapper.QuizMapper;
import com.example.sortquiz.viewmodel.QuizDetailViewModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizRepository {
    private final QuizMapper quizMapper;

    public QuizRepository(QuizMapper quizMapper) {
        this.quizMapper = quizMapper;
    }

    public List<Quiz> getQuizlist() {
        return quizMapper.getQuizlist();
    }

    public QuizDetailViewModel getDetailsByQuizId(long quizId) {
        return quizMapper.getDetailsByQuizId(quizId);
    }

    public List<Quiz> getAllQuizzesSortHappenYear() {
        return quizMapper.getAllQuizzesSortHappenYear();
    }

    public List<Quiz> getQuizzesByKeywordAndYear(String keyword, long yearMin, long yearMax) {
        return quizMapper.getQuizzesByKeywordAndYear(keyword, yearMin, yearMax);
    }
}
