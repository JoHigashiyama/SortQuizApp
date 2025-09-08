package com.example.sortquiz.repository;

import com.example.sortquiz.entity.User;
import com.example.sortquiz.mapper.RankingMapper;
import com.example.sortquiz.viewModel.UserViewModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RankingRepository {
    private final RankingMapper rankingMapper;

    public RankingRepository(RankingMapper rankingMapper) {
        this.rankingMapper = rankingMapper;
    }

    public List<UserViewModel> getTop10ByBestScore() {
        return rankingMapper.getTop10ByBestScore();
    }
}
