package com.example.sortquiz.viewModel;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserViewModel {
    private String username;
    private long score;
    private LocalDate createdAt;
    private long rank;
}
