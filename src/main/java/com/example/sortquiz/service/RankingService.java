package com.example.sortquiz.service;

import com.example.sortquiz.repository.RankingRepository;
import com.example.sortquiz.viewModel.UserViewModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingService {
    private final RankingRepository rankingRepository;

    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public List<UserViewModel> getTop10ByBestScore() {
        return rankingRepository.getTop10ByBestScore();
    }
}
