package com.example.sortquiz.service;

import com.example.sortquiz.entity.Quiz;
import com.example.sortquiz.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getQuizlist() {
        return quizRepository.getQuizlist();
    }
}
