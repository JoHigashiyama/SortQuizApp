package com.example.sortquiz.repository;

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
}
