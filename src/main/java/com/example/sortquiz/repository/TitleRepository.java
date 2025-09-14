package com.example.sortquiz.repository;

import com.example.sortquiz.entity.Title;
import com.example.sortquiz.entity.UserTitle;
import com.example.sortquiz.mapper.TitleMapper;
import com.example.sortquiz.entity.UserTitle;
import com.example.sortquiz.mapper.TitleMapper;
import com.example.sortquiz.viewmodel.AchievedTitleViewModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TitleRepository {

    private final TitleMapper titleMapper;
    public TitleRepository(TitleMapper titleMapper) {
        this.titleMapper = titleMapper;
    }


    public List<Title> getTitles() {
        return titleMapper.getTitles();
    }

    public List<UserTitle> getUserTitles(long userId) {
        return titleMapper.getUserTitles(userId);
    }

    public List<AchievedTitleViewModel> selectAchievedTitleByUserId(long userId) {
        return titleMapper.selectAchievedTitlesByUserId(userId);
    }

    public void createUserTitle(UserTitle userTitle) {
        titleMapper.createUserTitle(userTitle);
    }
}

