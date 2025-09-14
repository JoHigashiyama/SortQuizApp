package com.example.sortquiz.viewmodel;

import lombok.Data;

@Data
public class AchievedTitleViewModel {
    private long titleId;
    private String title;
    private boolean isAchieved;
}
