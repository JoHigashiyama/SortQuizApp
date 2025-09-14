package com.example.sortquiz.service;

import com.example.sortquiz.entity.Title;
import com.example.sortquiz.entity.UserTitle;
import com.example.sortquiz.repository.TitleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleService {
    private final TitleRepository titleRepository;

    public TitleService(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    public List<Title> getTitles(){
        return titleRepository.getTitles();
    }

    public List<UserTitle> getUserTitles(long userId){
        return titleRepository.getUserTitles(userId);
    }
}
