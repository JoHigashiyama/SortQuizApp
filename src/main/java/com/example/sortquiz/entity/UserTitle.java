package com.example.sortquiz.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Data
public class UserTitle {
    private long id;
    private long userId;
    private long titleId;
    private LocalDate createdAt;
}
