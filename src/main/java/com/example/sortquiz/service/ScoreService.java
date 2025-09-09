package com.example.sortquiz.service;

import com.example.sortquiz.entity.Score;
import com.example.sortquiz.repository.ScoreRepository;
import com.example.sortquiz.viewmodel.ScoreHistoryViewModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
//    1問の点数
    private long correctPoint = 100;
//    残り時間の点数
    private long timePoint = 15;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public List<ScoreHistoryViewModel> selectScoresByUserId(long userId) {
        return scoreRepository.selectScoresByUserId(userId);
    }

    public void createScore(Score score) {
        scoreRepository.createScore(score);
    }

    public long calculateScore(long correctAnswers, long time) {
        long point = 0;
        point += correctAnswers * correctPoint;
        if (time > 0) {
            point += time * timePoint;
        }
        return point;
    }
}
