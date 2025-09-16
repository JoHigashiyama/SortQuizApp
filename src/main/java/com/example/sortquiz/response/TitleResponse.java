package com.example.sortquiz.response;

import com.example.sortquiz.entity.Title;
import lombok.Data;

import java.util.List;

@Data
public class TitleResponse {
    private List<Title> achievedTitles;
}
