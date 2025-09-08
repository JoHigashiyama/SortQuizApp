package com.example.sortquiz.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private long userId;
    private String username;
    private String email;
    private String password;
    private long bestScore;
    private long totalScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
