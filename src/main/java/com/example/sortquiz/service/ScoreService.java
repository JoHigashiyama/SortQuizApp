package com.example.sortquiz.service;

import com.example.sortquiz.repository.ScoreRepository;
import com.example.sortquiz.viewmodel.ScoreHistoryViewModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public List<ScoreHistoryViewModel> selectScoresByUserId(long userId) {
        return scoreRepository.selectScoresByUserId(userId);
    }
}
