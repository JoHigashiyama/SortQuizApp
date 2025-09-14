package com.example.sortquiz.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserTitle {
    private long id;
    private long userId;
    private long titleId;
    private LocalDateTime createdAt;
}
