package com.example.sortquiz.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private long userId;
    private String email;
    private String username;
    private String password;
    private long bestScore;
    private long totalScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
