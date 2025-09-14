package com.example.sortquiz.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Title {
    private long titleId;
    private String title;
    private String description;
    private LocalDate createdAt;
}
