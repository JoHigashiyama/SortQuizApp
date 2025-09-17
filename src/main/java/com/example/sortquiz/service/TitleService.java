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
//        10問正解数
        long perfectCount = scoreRepository.selectPerfectCountByUserId(userId);
//        累計性回数
        long totalCorrectCount = scoreRepository.selectTotalCorrectCountByUserId(userId);
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

//        2: 5回以上プレイする
        if (!achievedTitles.get(1).isAchieved()) {
            if (scores.size() >= 5) {
                userTitle.setTitleId(2);
                titleRepository.createUserTitle(userTitle);
                System.out.print("達成："+achievedTitles.get(1).getTitle());
                achieveTitles.add(2L);
            }
        }

//        3: 10回以上プレイする
        if (!achievedTitles.get(2).isAchieved()) {
            if (scores.size() >= 10) {
                userTitle.setTitleId(3);
                titleRepository.createUserTitle(userTitle);
                System.out.print("達成："+achievedTitles.get(2).getTitle());
                achieveTitles.add(3L);
            }
        }

//        4: 初めて全問正解する
        if (!achievedTitles.get(3).isAchieved()) {
            if (perfectCount >= 1) {
                userTitle.setTitleId(4);
                titleRepository.createUserTitle(userTitle);
                System.out.print("達成："+achievedTitles.get(3).getTitle());
                achieveTitles.add(4L);
            }
        }

//        5: 5回以上10問正解する
        if (!achievedTitles.get(4).isAchieved()) {
            if (perfectCount >= 10) {
                userTitle.setTitleId(5);
                titleRepository.createUserTitle(userTitle);
                System.out.print("達成："+achievedTitles.get(4).getTitle());
                achieveTitles.add(5L);
            }
        }

//        6: 累計で10問正解する
        if (!achievedTitles.get(5).isAchieved()) {
            if (totalCorrectCount >= 10) {
                userTitle.setTitleId(6);
                titleRepository.createUserTitle(userTitle);
                System.out.print("達成："+achievedTitles.get(5).getTitle());
                achieveTitles.add(6L);
            }
        }

//        7: 累計で50問正解する
        if (!achievedTitles.get(6).isAchieved()) {
            if (totalCorrectCount >= 50) {
                userTitle.setTitleId(7);
                titleRepository.createUserTitle(userTitle);
                System.out.print("達成："+achievedTitles.get(6).getTitle());
                achieveTitles.add(7L);
            }
        }

//        8: 累計100問正解する
        if (!achievedTitles.get(7).isAchieved()) {
            if (totalCorrectCount >= 100) {
                userTitle.setTitleId(8);
                titleRepository.createUserTitle(userTitle);
                System.out.print("達成："+achievedTitles.get(7).getTitle());
                achieveTitles.add(8L);
            }
        }

//        9: 総合得点が1000点を突破
        if (!achievedTitles.get(8).isAchieved()) {
            if (user.getTotalScore() >= 1000) {
                userTitle.setTitleId(9);
                titleRepository.createUserTitle(userTitle);
                System.out.println("達成："+achievedTitles.get(8).getTitle());
                achieveTitles.add(9L);
            }
        }

//        10: 総合得点が5000点を突破
        if (!achievedTitles.get(9).isAchieved()) {
            if (user.getTotalScore() >= 5000) {
                userTitle.setTitleId(10);
                titleRepository.createUserTitle(userTitle);
                System.out.println("達成："+achievedTitles.get(8).getTitle());
                achieveTitles.add(10L);
            }
        }

//        11: 総合得点が20000点を突破
        if (!achievedTitles.get(10).isAchieved()) {
            if (user.getTotalScore() >= 20000) {
                userTitle.setTitleId(11);
                titleRepository.createUserTitle(userTitle);
                System.out.println("達成："+achievedTitles.get(10).getTitle());
                achieveTitles.add(11L);
            }
        }

        return achieveTitles;
    }

    public List<Title> getTitlesByTitleId(List<Long> titles) {
        List<Title> titleList = new ArrayList<>();
        for (long titleId : titles) {
            titleList.add(titleRepository.selectTitleByTitleId(titleId));
        }
        return titleList;
    }
}
