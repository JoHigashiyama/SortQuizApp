package com.example.sortquiz.repository;

import com.example.sortquiz.entity.Title;
import com.example.sortquiz.entity.UserTitle;
import com.example.sortquiz.mapper.TitleMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TitleRepository {
    private final TitleMapper titleMapper;

    public TitleRepository(TitleMapper titleMapper) {
        this.titleMapper = titleMapper;
    }

    public List<Title> getTitles(){
        return titleMapper.getTitles();
    }

    public List<UserTitle> getUserTitles(long userId){
        return titleMapper.getUserTitles(userId);
    }
}
