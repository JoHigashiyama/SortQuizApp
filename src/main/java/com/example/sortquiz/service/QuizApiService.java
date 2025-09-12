package com.example.sortquiz.service;

import com.example.sortquiz.entity.Quiz;
import com.example.sortquiz.form.QuizSearchForm;
import com.example.sortquiz.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class QuizApiService {
    private final QuizRepository quizRepository;

    public QuizApiService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getQuizzesByKeywordAndYear(QuizSearchForm form) {
//        最大値が設定されていない場合
        if (form.getYearMax() == 0) form.setYearMax(2100);
//        検索
        List<Quiz> quizzes = quizRepository.getQuizzesByKeywordAndYear(form);

        return quizzes;
    }
}
