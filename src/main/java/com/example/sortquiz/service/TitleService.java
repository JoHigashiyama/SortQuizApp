package com.example.sortquiz.service;

import com.example.sortquiz.entity.Score;
import com.example.sortquiz.entity.UserTitle;
import com.example.sortquiz.repository.ScoreRepository;
import com.example.sortquiz.repository.TitleRepository;
import com.example.sortquiz.viewmodel.AchievedTitleViewModel;
import com.example.sortquiz.viewmodel.ScoreHistoryViewModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleService {
    private final TitleRepository titleRepository;
    private final ScoreRepository scoreRepository;

    public TitleService(TitleRepository titleRepository, ScoreRepository scoreRepository) {
        this.titleRepository = titleRepository;
        this.scoreRepository = scoreRepository;
    }

    public void updateAchievedTitle(long userId) {
//        取得状況も含めた称号一覧を取得する
        List<AchievedTitleViewModel> achievedTitles = titleRepository.selectAchievedTitleByUserId(userId);

//        ここから確認と更新
        UserTitle userTitle = new UserTitle();
        userTitle.setUserId(userId);
//        更新の際にtitleIdを変更して、insertする
//        1: 初めてプレイする
        if (!achievedTitles.get(0).isAchieved()) {
            List<ScoreHistoryViewModel> scores = scoreRepository.selectScoresByUserId(userId);
            if (scores != null) {
                userTitle.setTitleId(0);
                titleRepository.createUserTitle(userTitle);
                System.out.println("達成："+achievedTitles.get(0).getTitle());
            }
        }

//        2: 初めて全問正解する

//        3: 5回以上プレイする
    }
}
