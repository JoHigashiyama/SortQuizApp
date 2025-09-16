package com.example.sortquiz.service;

import com.example.sortquiz.entity.Title;
import com.example.sortquiz.entity.UserTitle;
import com.example.sortquiz.repository.TitleRepository;
import com.example.sortquiz.entity.User;
import com.example.sortquiz.repository.ScoreRepository;
import com.example.sortquiz.repository.UserRepository;
import com.example.sortquiz.viewmodel.AchievedTitleViewModel;
import com.example.sortquiz.viewmodel.ScoreHistoryViewModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TitleService {
    private final TitleRepository titleRepository;
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    public TitleService(TitleRepository titleRepository, ScoreRepository scoreRepository, UserRepository userRepository) {
        this.titleRepository = titleRepository;
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
    }

    public List<Title> getTitles(){
        return titleRepository.getTitles();
    }

    public List<UserTitle> getUserTitles(long userId) {
        return titleRepository.getUserTitles(userId);
    }

    public List<Long> updateAchievedTitle(long userId) {
        List<Long> achieveTitles = new ArrayList<>();
//        取得状況も含めた称号一覧を取得する
        List<AchievedTitleViewModel> achievedTitles = titleRepository.selectAchievedTitleByUserId(userId);
//        プレイ履歴(スコア履歴)を取得する
        List<ScoreHistoryViewModel> scores = scoreRepository.selectScoresByUserId(userId);
//        ユーザー情報を取得する
        User user = userRepository.getUserInformation(userId);
//        ここから確認と更新
        UserTitle userTitle = new UserTitle();
        userTitle.setUserId(userId);
//        更新の際にtitleIdを変更して、insertする
//        1: 初めてプレイする
        if (!achievedTitles.get(0).isAchieved()) {
            if (scores != null) {
                userTitle.setTitleId(1);
                titleRepository.createUserTitle(userTitle);
                System.out.println("達成："+achievedTitles.get(0).getTitle());
                achieveTitles.add(1L);
            }
        }

//        2: 総合得点が1000点を突破
        if (!achievedTitles.get(1).isAchieved()) {
            if (user.getTotalScore() >= 1000) {
                userTitle.setTitleId(2);
                titleRepository.createUserTitle(userTitle);
                System.out.println("達成："+achievedTitles.get(1).getTitle());
                achieveTitles.add(2L);
            }
        }

//        3: 5回以上プレイする
        if (!achievedTitles.get(2).isAchieved()) {
            if (scores.size() >= 5) {
                userTitle.setTitleId(3);
                titleRepository.createUserTitle(userTitle);
                System.out.print("達成："+achievedTitles.get(2).getTitle());
                achieveTitles.add(3L);
            }
        }

//        4: 初めて全問正解する
        if (!achievedTitles.get(3).isAchieved()) {
            for (ScoreHistoryViewModel score : scores) {
                if (score.getCorrectCount() == 10) {
                    userTitle.setTitleId(4);
                    titleRepository.createUserTitle(userTitle);
                    System.out.print("達成："+achievedTitles.get(3).getTitle());
                    achieveTitles.add(4L);
                    break;
                }
            }
        }
        return achieveTitles;
    }
}
