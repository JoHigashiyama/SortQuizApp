package com.example.sortquiz.repository;

import com.example.sortquiz.entity.Quiz;
import com.example.sortquiz.mapper.QuizMapper;
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
}
