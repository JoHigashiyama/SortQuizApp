package com.example.sortquiz.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Score {
    private long scoreId;
    private long userId;
    private long score;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
