package com.example.sortquiz.repository;

import com.example.sortquiz.entity.Score;
import com.example.sortquiz.mapper.ScoreMapper;
import com.example.sortquiz.viewmodel.ScoreHistoryViewModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScoreRepository {
    private final ScoreMapper scoreMapper;

    public ScoreRepository(ScoreMapper scoreMapper) {
        this.scoreMapper = scoreMapper;
    }

    public List<ScoreHistoryViewModel> selectScoresByUserId(long userId) {
        return scoreMapper.selectScoresByUserId(userId);
    }

    public List<ScoreHistoryViewModel> selectScoresScoreByUserId(long userId){
        return scoreMapper.selectScoresScoreByUserId(userId);
    }

    public void createScore(Score score) {
        scoreMapper.createScore(score);
    }

    public long selectBestScoreByUserId(long userId) {
        return scoreMapper.selectBestScoreByUserId(userId);
    }

    public long selectTotalScoreByUserId(long userId) {
        return scoreMapper.selectTotalScoreByUserId(userId);
    }

    public long selectPerfectCountByUserId(long userId) {
        return scoreMapper.selectPerfectCountByUserId(userId);
    }

    public long selectTotalCorrectCountByUserId(long userId) {
        return scoreMapper.selectTotalCorrectCountByUserId(userId);
    }
}
