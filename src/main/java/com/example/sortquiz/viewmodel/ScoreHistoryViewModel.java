package com.example.sortquiz.viewmodel;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScoreHistoryViewModel {
    private String username;
    private long score;
    private LocalDate createdAt;
    private long rank;
}
