package com.example.sortquiz.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Quiz {
    private long quizId;
    private String content;
    private int happenYear;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
