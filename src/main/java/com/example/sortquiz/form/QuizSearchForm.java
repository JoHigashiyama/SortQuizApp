package com.example.sortquiz.form;

import lombok.Data;

@Data
public class QuizSearchForm {
    private String keyword;
    private long yearMin;
    private long yearMax;
}
